package com.example.finalproject.repository;

import com.example.finalproject.domain.Notice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserNoticeRepository extends JpaRepository<Notice, Long> {
}
