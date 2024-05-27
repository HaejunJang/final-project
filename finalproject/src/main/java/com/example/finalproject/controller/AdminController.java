package com.example.finalproject.controller;

import com.example.finalproject.dto.AdminLoginDto;
import com.example.finalproject.dto.TokenInfo;
import com.example.finalproject.service.admin.AdminService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequiredArgsConstructor
@Slf4j
public class AdminController {
//관리자 로그인, 로그아웃
    @Autowired
    private AdminService adminService;
    //GET

    //POST
    //관리자 로그인
    @PostMapping("/src/admins/login")
    public ResponseEntity<TokenInfo> adminLogin(@RequestBody AdminLoginDto adminLoginDto) {
        String adminId = adminLoginDto.getAdminId();
        String adminPw = adminLoginDto.getAdminPw();
        TokenInfo tokenInfo = adminService.login(adminId,adminPw);
        return ResponseEntity.ok(tokenInfo);
    }
    //로그아웃 요청
    @PostMapping("/src/admins/logout")
    public ResponseEntity<String> adminLogout() {
        SecurityContextHolder.clearContext();
        return ResponseEntity.status(HttpStatus.OK).body("{\"message\": \"Logout success\"}");
    }

    //테스트 용도
    @PostMapping("/src/admins/test")
    public ResponseEntity<String> test() {
        String message = "success";

    return ResponseEntity.status(HttpStatus.OK).body("{\"message\": \"" + message + "\"}");
}
}
