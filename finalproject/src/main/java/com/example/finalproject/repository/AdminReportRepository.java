package com.example.finalproject.repository;

import com.example.finalproject.domain.Report;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AdminReportRepository extends JpaRepository<Report, Long> {
    @Query("SELECT r FROM Report r ORDER BY r.reportNum DESC")
    List<Report> findAllOrderByReportNumDesc();
}
