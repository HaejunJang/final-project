package com.example.finalproject.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AdminDataDto {
    //관리자 데이터 등록 dto
    private String dataDate;    //측정시간
    private String dataGu;      //자치구
    private String dataDong;    //행정동
    private int dataPeople;     //유동인구
}
