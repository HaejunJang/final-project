package com.example.finalproject.controller;

import com.example.finalproject.dto.AdminFeedbackDto;
import com.example.finalproject.service.admin.AdminFeedbackService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Slf4j
public class AdminFeedbackController {

    @Autowired
    private AdminFeedbackService adminFeedbackService;

    @GetMapping("/src/admins/feedbacks")
    public ResponseEntity<List<AdminFeedbackDto>> getAllFeedbacks() {
        List<AdminFeedbackDto> feedbackDtos = adminFeedbackService.getAllFeedbacksWithUserName();
        log.info("회원 피드백 조회에 성공했습니다.");
        return new ResponseEntity<>(feedbackDtos, HttpStatus.OK);
    }
}
