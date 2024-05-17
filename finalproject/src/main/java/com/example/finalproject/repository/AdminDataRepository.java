package com.example.finalproject.repository;

import com.example.finalproject.domain.Data;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AdminDataRepository extends JpaRepository<Data, Long> {
    //유동인구 데이터 리퍼지토리
}
