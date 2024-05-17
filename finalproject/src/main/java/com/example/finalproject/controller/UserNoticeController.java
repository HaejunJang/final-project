package com.example.finalproject.controller;

import com.example.finalproject.dto.UserNoticeDto;
import com.example.finalproject.service.user.UserNoticeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class UserNoticeController {

    @Autowired
    private UserNoticeService userNoticeService;

    //공지 리스트 조회
    @GetMapping("/src/users/notices")
    public ResponseEntity<List<UserNoticeDto>> getAllNotices() {
        List<UserNoticeDto> notices = userNoticeService.getAllNotices();
        return new ResponseEntity<>(notices, HttpStatus.OK);
    }
    
    //공지 세부정보 조회
    @GetMapping("/src/users/notices/{postNum}")
    public ResponseEntity<UserNoticeDto> getNoticeById(@PathVariable("postNum") Long postNum) {
        UserNoticeDto notice = userNoticeService.getNoticeByNum(postNum);
        if(notice != null) {
            return new ResponseEntity<>(notice, HttpStatus.OK);
        }else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
