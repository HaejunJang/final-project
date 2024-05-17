package com.example.finalproject.domain;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "setting")
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Setting {
    //이젠 사용안함
    //회원 맞춤 서비스
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // Service 엔티티의 기본 키

    @ManyToOne
    @JoinColumn(name = "userNum", nullable = false)
    private User user; //
    
    @Column(name = "placed")
    private String placed;  //장소

//    @Column(name = "time")
//    private String time;    //시간
    // 생성자, 게터, 세터 등 생략
}

