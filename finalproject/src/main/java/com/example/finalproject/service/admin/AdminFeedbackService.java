package com.example.finalproject.service.admin;

import com.example.finalproject.domain.Feedback;
import com.example.finalproject.dto.AdminFeedbackDto;
import com.example.finalproject.repository.AdminFeedbackRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AdminFeedbackService {

    @Autowired
    private AdminFeedbackRepository adminFeedbackRepository;

    public List<AdminFeedbackDto> getAllFeedbacksWithUserName() {
        List<Feedback> feedbacks = adminFeedbackRepository.findAll();
        return feedbacks.stream()
                .map(this::converToDto)
                .collect(Collectors.toList());
    }

    private AdminFeedbackDto converToDto(Feedback feedback) {
        AdminFeedbackDto feedbackDto = new AdminFeedbackDto();
        feedbackDto.setFeedNum(feedback.getFeedNum());
        feedbackDto.setFeedGrade(feedback.getFeedGrade());
        feedbackDto.setFeedContent(feedback.getFeedContent());
        feedbackDto.setUserName(feedback.getUser().getUsername());
        return feedbackDto;
    }
}
