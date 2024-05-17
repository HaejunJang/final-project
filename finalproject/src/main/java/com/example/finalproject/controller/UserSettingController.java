package com.example.finalproject.controller;

import com.example.finalproject.dto.UserSettingRequestDto;
import com.example.finalproject.dto.UserSettingResponseDto;
import com.example.finalproject.service.user.UserSettingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserSettingController {

    //맞춤형 서비스 처리
    @Autowired
    private UserSettingService userSettingService;

    @PostMapping("/src/users/service/request")
    public ResponseEntity<?> requestService(@RequestBody UserSettingRequestDto userSettingRequestDto) {
        UserSettingResponseDto responseDto = userSettingService.saveService(userSettingRequestDto);
        if (responseDto != null) {
            return ResponseEntity.ok(responseDto);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("{\"time\": \"데이터 없습니다.\", \"population\": \"데이터 없습니다.\"}");
        }
    }
}

