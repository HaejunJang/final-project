package com.example.finalproject.domain;

import jakarta.persistence.*;
import lombok.*;

@Table(name = "ai_model")
@Entity
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class AiModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int modelNum; //현재 모델 번호

    @Column(name = "modelName",nullable = false)
    private String modelName; // 모델 이름  -> 생성일자 서버에서 시간찍기

    @Column(name = "batchSize",nullable = true)
    private Integer batchSize; //배치 크기

    @Column(name = "epoch",nullable = true)
    private Integer epoch;      //에포크 수

    @Column(name = "isActive", nullable = false)
    private boolean isActive;   //활성화 여부
}