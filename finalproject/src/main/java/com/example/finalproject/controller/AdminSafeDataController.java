package com.example.finalproject.controller;

import com.example.finalproject.dto.AdminSafeDataRequestDto;
import com.example.finalproject.service.admin.AdminSafeDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
public class AdminSafeDataController {
    @Autowired
    private AdminSafeDataService dataService;
    
    //엑셀파일 등록
    @PostMapping("/src/admins/safeThing/{safeName}")
    public ResponseEntity<String> uploadData(@PathVariable("safeName") String safeName, @RequestParam("file") MultipartFile csvFile) {
        dataService.processCsvFile(csvFile, safeName);
        return ResponseEntity.ok("{\"message\": \"데이터 업로드 완료.\"}");
    }

    //데이터 조회
    @GetMapping("/src/admins/safeThing/{safeName}")
    public ResponseEntity<List<AdminSafeDataRequestDto>> getSafeThingsBySafeName(@PathVariable("safeName") String safeName) {
        List<AdminSafeDataRequestDto> safeThings = dataService.getSafeThingsBySafeName(safeName);
        return ResponseEntity.ok(safeThings);
    }

    //데이터 삭제
    @DeleteMapping("/src/admins/safeThing/{safeNum}")
    public ResponseEntity<String> deleteData(@PathVariable("safeNum") Long safeNum) {
        boolean deleted = dataService.deleteData(safeNum);
        if (deleted) {
            String message = "{\"message\": \"데이터 삭제를 성공했습니다.\"}";
            return ResponseEntity.status(HttpStatus.OK).body(message);
        } else {
            String message = "{\"message\": \"해당하는 데이터가 없습니다.\"}";
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(message);
        }
    }
}
