package com.example.finalproject.repository;

import com.example.finalproject.domain.Report;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AdminReportRepository extends JpaRepository<Report, Long> {
    //신고 관리
}
