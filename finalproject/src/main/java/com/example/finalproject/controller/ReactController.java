package com.example.finalproject.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ReactController {
    @RequestMapping(value = {"/", "/other-path/**"}) // "/other-path/**"는 React 라우터에 의해 처리되는 경로 예시
    public String serveIndexHtml() {
        return "forward:/index.html";
    }
}
