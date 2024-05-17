package com.example.finalproject.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AdminTestController {
    @GetMapping("/image-url")
    @ResponseBody
    public String provideImageUrl() {
        // 이미지 경로를 직접 반환
        return "/images/myimage.jpg";
    }
}
