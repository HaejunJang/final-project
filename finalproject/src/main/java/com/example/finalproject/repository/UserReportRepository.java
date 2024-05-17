package com.example.finalproject.repository;

import com.example.finalproject.domain.Report;
import com.example.finalproject.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserReportRepository extends JpaRepository<Report, Long> {
    List<Report> findByUser(User user);
}
