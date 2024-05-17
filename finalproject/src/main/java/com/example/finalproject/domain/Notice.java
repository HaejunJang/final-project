package com.example.finalproject.domain;

import jakarta.persistence.*;
import lombok.*;

@Table(name = "notice")
@Entity
@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Notice {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long postNum; // 공지사항의 PK

    @ManyToOne // 관리자와의 다대일 관계
    @JoinColumn(name = "adminNum") // 외래키로 사용될 컬럼을 지정
    private Admin admin; // 관리자 엔티티와 연결

    @Column(name = "postName",nullable = false)
    private String postName; // 공지사항 제목
    
    @Column(name = "postContent")
    private String postContent; //공지사항 내용
    
    @Column(name = "postDate",nullable = false)
    private String postDate;   //공지사항 게시한 날짜
}
