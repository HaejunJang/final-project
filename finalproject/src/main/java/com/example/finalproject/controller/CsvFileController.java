package com.example.finalproject.controller;

import com.example.finalproject.service.admin.CsvFileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
public class CsvFileController {

    @Autowired
    private CsvFileService fileService;
    //임시 파일인 csv 파일 예측 데이터 등록
    @PostMapping("/src/admins/CsvFileUpload")
    public ResponseEntity<String> uploadFile(@RequestParam("file") MultipartFile csvFile) {
        fileService.processCsvFile(csvFile);
        return ResponseEntity.ok("{\"message\": \"데이터 업로드 완료.\"}");
    }
}
