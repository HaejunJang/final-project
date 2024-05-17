package com.example.finalproject.controller;

import com.example.finalproject.domain.Data;
import com.example.finalproject.dto.AdminDataDto;
import com.example.finalproject.service.admin.AdminDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
public class AdminDataController {
    //유동인구 데이터 컨트롤러
    @Autowired
    private AdminDataService adminDataService;

    //관리자 데이터 엑셀파일 업로드
    @PostMapping("/src/admins/flowPop/uploadExcelFile")
    public ResponseEntity<String> uploadData(@RequestParam("file") MultipartFile csvFile) {
        adminDataService.processCsvFile(csvFile);
        return ResponseEntity.ok("{\"message\": \"데이터 업로드 완료.\"}");
    }

    //데이터 조회
    @GetMapping("/src/admins/flowPop")
    public ResponseEntity<List<Data>> getAllData() {
        List<Data> dataList = adminDataService.getAllData();
        if (!dataList.isEmpty()) {
            return new ResponseEntity<>(dataList, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    //데이터 등록
    @PostMapping("/src/admins/flowPop")
    public ResponseEntity<Data> addData(@RequestBody AdminDataDto dataDto) {
        Data savedData = adminDataService.addData(dataDto);
        return new ResponseEntity<>(savedData, HttpStatus.CREATED);
    }

    //데이터 삭제
    @DeleteMapping("/src/admins/flowPop/{dataNum}")
    public ResponseEntity<String> deleteData(@PathVariable("dataNum") Long dataNum) {
        boolean deleted = adminDataService.deleteData(dataNum);
        if (deleted) {
            String message = "{\"message\": \"데이터 삭제를 성공했습니다.\"}";
            return ResponseEntity.status(HttpStatus.OK).body(message);
        } else {
            String message = "{\"message\": \"해당하는 데이터가 없습니다.\"}";
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(message);
        }

    }
}
