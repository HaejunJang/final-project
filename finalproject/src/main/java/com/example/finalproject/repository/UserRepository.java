package com.example.finalproject.repository;

import com.example.finalproject.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    
     boolean existsByUserId(String userId);

    Optional<User> findByUserId(String userId);

    User findByUserNum(Long userNum);  //관리자 회원 조회 서비스에서 사용
}
