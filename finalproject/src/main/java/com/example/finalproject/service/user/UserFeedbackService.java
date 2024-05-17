package com.example.finalproject.service.user;

import com.example.finalproject.domain.Feedback;
import com.example.finalproject.domain.User;
import com.example.finalproject.dto.UserFeedbackDto;
import com.example.finalproject.repository.UserFeedbackRepository;
import com.example.finalproject.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserFeedbackService {

    @Autowired
    private UserFeedbackRepository userFeedbackRepository;

    @Autowired
    private UserRepository userRepository;

    @Transactional
    public void addFeedback(UserFeedbackDto feedbackDto, String userId) {
        User user = userRepository.findByUserId(userId)
                .orElseThrow(()->new IllegalArgumentException("해당하는 사용자가 없습니다."));

        Feedback feedback = new Feedback();
        feedback.setUser(user);
        feedback.setFeedGrade(feedbackDto.getFeedGrade());
        feedback.setFeedContent(feedbackDto.getFeedContent());

        userFeedbackRepository.save(feedback);
    }
}
