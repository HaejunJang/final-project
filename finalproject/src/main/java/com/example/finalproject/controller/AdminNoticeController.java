package com.example.finalproject.controller;

import com.example.finalproject.dto.AdminNoticeRequestDto;
import com.example.finalproject.dto.AdminNoticeResponseDto;
import com.example.finalproject.service.admin.AdminNoticeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class AdminNoticeController {

    @Autowired
    private AdminNoticeService adminNoticeService;

    //관리자 공지 리스트 조회
    @GetMapping("/src/admins/notices")
    public ResponseEntity<List<AdminNoticeResponseDto>> getAllData() {
        List<AdminNoticeResponseDto> dataList = adminNoticeService.getAllData();
        if (!dataList.isEmpty()) {
            return new ResponseEntity<>(dataList, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }


    //관리자 공지 등록
    @PostMapping("/src/admins/notice")
    public ResponseEntity<String> createNotice(@RequestBody AdminNoticeRequestDto noticeRequestDto) {
        adminNoticeService.saveNotice(noticeRequestDto);
        return ResponseEntity.ok("{\"message\": \"공지가 성공적으로 등록되었습니다.\"}");
    }

    //관리자 공지 삭제
    @DeleteMapping("/src/admins/notice/{postNum}")
    public ResponseEntity<String> deletePost(@PathVariable("postNum") Long postNum) {
        boolean deleted = adminNoticeService.deletePost(postNum);
        if (deleted) {
            String message = "{\"message\": \"데이터 삭제를 성공했습니다.\"}";
            return ResponseEntity.status(HttpStatus.OK).body(message);
        } else {
            String message = "{\"message\": \"해당하는 데이터가 없습니다.\"}";
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(message);
        }
    }
}
