package com.example.finalproject.controller;

import com.example.finalproject.dto.UserFeedbackDto;
import com.example.finalproject.service.user.UserFeedbackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserFeedbackController {

    @Autowired
    private UserFeedbackService userFeedbackService;

    @PostMapping("/src/users/feedback")
    public ResponseEntity<String> addFeedback(@RequestBody UserFeedbackDto feedbackDto) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userId = authentication.getName();

        userFeedbackService.addFeedback(feedbackDto,userId);
        return ResponseEntity.status(HttpStatus.OK).body("{\"message\": \"리뷰가 작성되었습니다.\"}");
    }
}
