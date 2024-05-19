package com.example.finalproject.controller;

import com.example.finalproject.dto.AdminUserDto;
import com.example.finalproject.service.admin.AdminUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class AdminUserController {

    @Autowired
    private AdminUserService adminUserService;

    //회원 조회
    @GetMapping("/src/admins/users")
    public ResponseEntity<List<AdminUserDto>> getAllUsers() {
        List<AdminUserDto> users = adminUserService.getAllUsers();
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    //회원 상세 조회  사용 안하는걸로 변경
    @GetMapping("/src/admins/users/{userNum}")
    public ResponseEntity<Object> getUserByUserNum(@PathVariable("userNum") Long userNum) {
        AdminUserDto userDto = adminUserService.getUserByUserNum(userNum);
        if(userDto != null) {
            return new ResponseEntity<>(userDto,HttpStatus.OK);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("{\"message\": \"회원이 없습니다.\"}");
        }
    }
}
