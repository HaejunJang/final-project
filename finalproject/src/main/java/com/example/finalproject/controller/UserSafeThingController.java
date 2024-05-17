package com.example.finalproject.controller;

import com.example.finalproject.dto.UserSafeThingDto;
import com.example.finalproject.service.user.UserSafeThingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class UserSafeThingController {

    @Autowired
    private UserSafeThingService userSafeThingService;

    //데이터 요청
    @GetMapping("/src/users/safeThings")
    public ResponseEntity<List<UserSafeThingDto>> getAllData() {
        List<UserSafeThingDto> safeThings = userSafeThingService.getAllSafeThings();
        return new ResponseEntity<>(safeThings, HttpStatus.OK);
    }
}
