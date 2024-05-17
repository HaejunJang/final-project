package com.example.finalproject.domain;

import jakarta.persistence.*;
import lombok.*;

@Table(name = "data")
@Entity
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Data {

    @Id //pk 지정
    @GeneratedValue(strategy = GenerationType.IDENTITY) //DB가 자동으로 1씩 증가
    private Long dataNum;      //데이터 번호

    @Column(name = "dataDate",nullable = false)
    private String dataDate;   //측정 시간

    @Column(name = "dataGu",nullable = false)
    private String dataGu;     //자치구

    @Column(name = "dataDong",nullable = false)
    private String dataDong;   //행정동

    @Column(name = "dataPeople",nullable = false)
    private int dataPeople; //방문자 수
}
