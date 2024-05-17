package com.example.finalproject.repository;

import com.example.finalproject.domain.Feedback;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdminFeedbackRepository extends JpaRepository<Feedback, Long> {
}
