package com.example.finalproject.repository;

import com.example.finalproject.domain.Notice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AdminNoticeRepository extends JpaRepository<Notice, Long> {
    @Query("SELECT n FROM Notice n ORDER BY n.postNum DESC")
    List<Notice> findAllOrderByPostNumDesc();
}
