package com.example.finalproject.domain;

import jakarta.persistence.*;
import lombok.*;

@Table(name = "prediction")
@Entity
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Prediction {
    //미리 예측한 csv 파일 읽어온 데이터
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long dataNum;       //데이터 식별번호

    @Column(name = "address")
    private String address;     //주소

    @Column(name = "time")
    private String time;        //시간

    @Column(name = "flow")
    private String flow;        //붐빔 정도
}
