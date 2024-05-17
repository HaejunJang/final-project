package com.example.finalproject.repository;

import com.example.finalproject.domain.Feedback;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserFeedbackRepository extends JpaRepository<Feedback, Long> {

}
