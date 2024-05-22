package com.example.finalproject.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import com.example.finalproject.domain.Feedback;

import java.util.List;


@Repository
public interface AdminFeedbackRepository extends JpaRepository<Feedback, Long> {
    //관리자 피드백 내림차순 조회
    @Query("SELECT r FROM Feedback r ORDER BY r.feedNum DESC")
    List<Feedback> findAllOrderByFeedNumDesc();
}
