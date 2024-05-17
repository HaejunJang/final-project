package com.example.finalproject.domain;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "report")
@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Report {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long reportNum; // 신고 번호, PK로 지정

    @ManyToOne
    @JoinColumn(name = "userNum") // USER 테이블의 USER_NUM을 외래키(FK)로 지정
    private User user;

    @Column(name = "reportPlaced",nullable = false)
    private String reportPlaced; // 신고 위치

    @Column(name = "reportCategory",nullable = false)
    private String reportCategory; //신고 카테고리

    @Column(name = "reportDetails",nullable = false)
    private String reportDetails; // 신고 상세정보

    @Column(name = "reportDegree", nullable = false)
    private String reportDegree;    //신고 긴급 정도

    @Column(name = "reportTime",nullable = false)
    private String reportTime; // 신고 시각

}
