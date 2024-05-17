package com.example.finalproject.service.admin;

import com.example.finalproject.domain.Data;
import com.example.finalproject.dto.AdminDataDto;
import com.example.finalproject.repository.AdminDataRepository;
import io.jsonwebtoken.io.IOException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class AdminDataService {

    @Autowired
    private AdminDataRepository adminDataRepository;

    //데이터 조회
    public List<Data> getAllData() {
        return adminDataRepository.findAll();
    }

    //데이터 등록
    public Data addData(AdminDataDto dataDto) {
        Data data = new Data();
        data.setDataDate(dataDto.getDataDate());
        data.setDataGu(dataDto.getDataGu());
        data.setDataDong(dataDto.getDataDong());
        data.setDataPeople(dataDto.getDataPeople());
        return adminDataRepository.save(data);
    }
    //데이터 삭제
    public boolean deleteData(Long dataNum) {
        Optional<Data> optionalData = adminDataRepository.findById(dataNum);
        if (optionalData.isPresent()) {
            adminDataRepository.delete(optionalData.get());
            return true; // 삭제 성공
        } else {
            return false; // 삭제 실패 (데이터가 존재하지 않음)
        }
    }

    //엑셀 파일 등록
    public void processCsvFile(MultipartFile csvFile) {
        try {
            List<AdminDataDto> dataDtos = readCsvData(csvFile);
            saveDataToDatabase(dataDtos);
            log.info("데이터를 성공적으로 저장했습니다.");
        }catch (IOException e) {
            log.error("CSV파일을 처리하는중 오류가 발생했습니다.");
            e.printStackTrace();
        }
    }

    private List<AdminDataDto> readCsvData(MultipartFile csvFile) throws IOException {
        List<AdminDataDto> dataDtos = new ArrayList<>();

        try(BufferedReader reader = new BufferedReader(new InputStreamReader(csvFile.getInputStream()))) {
            String line;

            //헤더 라인은 건너뛰고 데이터를 읽습니다.
            reader.readLine();

            while ((line = reader.readLine()) != null) {
                String[] fields = line.split(",");//쉼표로 분할하여 필드 추출
                if(fields.length != 4) {
                    //필드 수가 예상과 다르면 스킵하거나 예외 처리를 할수 있습니다.
                    continue;
                }
                AdminDataDto dataDto = new AdminDataDto(
                        fields[0],  //측정시간
                        fields[1],  //자치구
                        fields[2],  //행정동
                        Integer.parseInt(fields[3]) //유동인구
                );
                dataDtos.add(dataDto);
            }
            log.info("CSV 파일을 성공적으로 읽었습니다.");
        } catch (java.io.IOException e) {
            throw new RuntimeException(e);
        }
        return dataDtos;
    }

    private void saveDataToDatabase(List<AdminDataDto> dataDtos){
        List<Data> dataList = new ArrayList<>();
        for(AdminDataDto dataDto : dataDtos) {
            Data data = new Data();
            data.setDataDate(dataDto.getDataDate());
            data.setDataGu(dataDto.getDataGu());
            data.setDataDong(dataDto.getDataDong());
            data.setDataPeople(dataDto.getDataPeople());
            dataList.add(data);
        }
        adminDataRepository.saveAll(dataList);
        log.info("데이터베이스에 데이터를 저장했습니다.");
    }

}
