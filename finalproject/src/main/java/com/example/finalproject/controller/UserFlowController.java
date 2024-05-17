package com.example.finalproject.controller;

import com.example.finalproject.dto.UserFlowResponseDto;
import com.example.finalproject.service.user.UserFlowService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class UserFlowController {
    //회원 붐빔정도 예측 컨트롤러

    @Autowired
    private UserFlowService userFlowService;

    //플러터측에서 붐빔정도 요청시 반환
    @GetMapping("/src/users/flow")
    public ResponseEntity<List<UserFlowResponseDto>> getUserFlows() {
        List<UserFlowResponseDto> userFlowResponseDtos = userFlowService.getUserFlows();
        return new ResponseEntity<>(userFlowResponseDtos, HttpStatus.OK);
    }
}
