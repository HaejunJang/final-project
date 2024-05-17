package com.example.finalproject.controller;

import com.example.finalproject.dto.AdminMainDto;
import com.example.finalproject.service.admin.AdminMainService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class AdminMainController {

    //관리자 메인 페이지 컨트롤러
    @Autowired
    private AdminMainService adminMainService;

    @GetMapping("/src/admins/main")
    public ResponseEntity<List<AdminMainDto>> getMainDatas(){
        List<AdminMainDto> datas = adminMainService.getMainDatas();
        return ResponseEntity.ok(datas);
    }

}
