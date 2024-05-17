package com.example.finalproject.repository;

import com.example.finalproject.domain.Prediction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserFlowRepository extends JpaRepository<Prediction, Long> {
    List<Prediction> findByTime(String formattedDateTime);
}
