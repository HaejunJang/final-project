package com.example.finalproject.repository;

import com.example.finalproject.domain.AiModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AdminModelRepository extends JpaRepository<AiModel, Integer> {
    List<AiModel> findByModelNumNot(int modelNum);

    //활성화 되어있는 모델 찾기
    Optional<AiModel> findByIsActiveTrue();
    
}
