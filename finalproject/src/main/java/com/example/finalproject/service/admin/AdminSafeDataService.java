package com.example.finalproject.service.admin;

import com.example.finalproject.domain.SafeThing;
import com.example.finalproject.domain.SafeThingType;
import com.example.finalproject.dto.AdminSafeDataDto;
import com.example.finalproject.dto.AdminSafeDataRequestDto;
import com.example.finalproject.repository.AdminSafeThingRepository;
import com.example.finalproject.repository.AdminSafeThingTypeRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
public class AdminSafeDataService {

    @Autowired
    private AdminSafeThingRepository safeThingRepository;

    @Autowired
    private AdminSafeThingTypeRepository safeThingTypeRepository;

    public void processCsvFile(MultipartFile csvFile, String safeName) {
        try {
            List<AdminSafeDataDto> dataDtos = readCsvData(csvFile);
            int safeTypeNum = getSafeTypeNum(safeName);

            if (safeTypeNum != 0) {
                saveDataToDatabase(dataDtos, safeTypeNum);
                log.info("데이터를 성공적으로 저장했습니다.");
            } else {
                log.error("안전시설 구분 번호를 가져올 수 없습니다.");
            }
        } catch (IOException e) {
            log.error("CSV 파일을 처리하는 중 오류가 발생했습니다.");
            e.printStackTrace();
        }
    }

    private List<AdminSafeDataDto> readCsvData(MultipartFile csvFile) throws IOException {
        List<AdminSafeDataDto> dataDtos = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(csvFile.getInputStream()))) {
            String line;

            // 헤더 라인은 건너뛰고 데이터를 읽습니다.
            reader.readLine();

            while ((line = reader.readLine()) != null) {
                String[] fields = line.split(","); // 쉼표로 분할하여 필드 추출
                if (fields.length != 3) {
                    // 필드 수가 예상과 다르면 스킵하거나 예외 처리할 수 있습니다.
                    continue;
                }
                AdminSafeDataDto dataDto = new AdminSafeDataDto(
                        fields[0], // 주소
                        Double.parseDouble(fields[1]), // 위도
                        Double.parseDouble(fields[2]), // 경도
                        0
                );
                dataDtos.add(dataDto);
            }
            log.info("CSV 파일을 성공적으로 읽었습니다.");
        }
        return dataDtos;
    }

    private void saveDataToDatabase(List<AdminSafeDataDto> dataDtos, int safeTypeNum) {
        SafeThingType safeThingType = safeThingTypeRepository.findBySafeTypeNum(safeTypeNum);

        List<SafeThing> safeThings = new ArrayList<>();
        for (AdminSafeDataDto dataDto : dataDtos) {
            SafeThing safeThing = new SafeThing();
            safeThing.setSafeAddress(dataDto.getSafeAddress());
            safeThing.setSafeX(dataDto.getSafeX());
            safeThing.setSafeY(dataDto.getSafeY());
            safeThing.setSafeThingType(safeThingType);
            safeThings.add(safeThing);
        }
        safeThingRepository.saveAll(safeThings);
        log.info("데이터베이스에 데이터를 저장했습니다.");
    }

        private int getSafeTypeNum(String safeTypeName) {
            SafeThingType safeThingType = safeThingTypeRepository.findBySafeName(safeTypeName);
            return safeThingType != null ? safeThingType.getSafeTypeNum() : 0;
        }

        //데이터 이름으로 가져오기
        public List<AdminSafeDataRequestDto> getSafeThingsBySafeName(String safeName) {
            int safeTypeNum = getSafeTypeNum(safeName);
            if(safeTypeNum ==0) {
                log.error("안전시설 구분 번호가 없습니다.");
                return Collections.emptyList();
            }

            List<SafeThing> safeThings = safeThingRepository.findAllBySafeThingTypeSafeTypeNum(safeTypeNum);
            return safeThings.stream()
                    .map(this::convertToDto)
                    .collect(Collectors.toList());
        }


        private AdminSafeDataRequestDto convertToDto(SafeThing safeThing) {
            return new AdminSafeDataRequestDto(
                    safeThing.getSafeNum(),
                    safeThing.getSafeAddress(),
                    safeThing.getSafeY(),
                    safeThing.getSafeX()
            );
        }

    //데이터 삭제
    public boolean deleteData(Long safeNum) {
        Optional<SafeThing> optionalData = safeThingRepository.findById(safeNum);
        if (optionalData.isPresent()) {
            safeThingRepository.delete(optionalData.get());
            return true; // 삭제 성공
        } else {
            return false; // 삭제 실패 (데이터가 존재하지 않음)
        }
        
    }
}
