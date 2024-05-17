package com.example.finalproject.repository;

import com.example.finalproject.domain.SafeThing;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AdminSafeThingRepository extends JpaRepository<SafeThing, Long> {

    //관리자 데이터 조회
    List<SafeThing> findAllBySafeThingTypeSafeTypeNum(int safeTypeNum);
}


