package com.example.finalproject.service.admin;

import com.example.finalproject.domain.Prediction;
import com.example.finalproject.dto.CsvFileRequestDto;
import com.example.finalproject.repository.CsvFileRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class CsvFileService {

    @Autowired
    private CsvFileRepository csvFileRepository;
    //엑셀 파일 등록
    public  void processCsvFile(MultipartFile csvFile) {
        try {
            List<CsvFileRequestDto> dtos = readCsvData(csvFile);
            saveDataToDatabase(dtos);
            log.info("데이터를 성공적으로 저장했습니다.");
        } catch (IOException e) {
            log.error("csv파일을 처리하는중 오류가 발생");
            e.printStackTrace();
        }
    }

    private static List<CsvFileRequestDto> readCsvData(MultipartFile csvFile) throws IOException{
        List<CsvFileRequestDto> dataDtos = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(csvFile.getInputStream()))) {
            String line;

            //헤더 라인은 건너뛰고 데이터를 읽습니다.
            reader.readLine();

            while ((line = reader.readLine()) != null) {
                String[] fields = line.split(",");
                if (fields.length != 3) {
                    // 필드 수가 예상과 다르면 스킵하거나 예외 처리를 할수 있습니다.
                    continue;
                }
                CsvFileRequestDto dataDto = new CsvFileRequestDto(
                        fields[0],  //시간
                        fields[1],  //주소
                        fields[2]   //붐빔정도
                );
                dataDtos.add(dataDto);
            }
            log.info("csv 파일을 성공적으로 읽었습니다.");
        }catch (java.io.IOException e) {
            throw new RuntimeException(e);
        }
        return dataDtos;
    }

    private void saveDataToDatabase(List<CsvFileRequestDto> dataDtos) {
        List<Prediction> predictionList = new ArrayList<>();
        for(CsvFileRequestDto dataDto : dataDtos) {
            Prediction prediction = new Prediction();
            prediction.setTime(dataDto.getTime());
            prediction.setAddress(dataDto.getAddress());
            prediction.setFlow(dataDto.getFlow());
            predictionList.add(prediction);
        }
        csvFileRepository.saveAll(predictionList);
        log.info("데이터베이스에 데이터를 저장했습니다.");
    }
}
