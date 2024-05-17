package com.example.finalproject.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CsvFileRequestDto {
    //임시 예측 데이터 넣는 dto
    private String time;        //시간
    private String address;     //주소
    private String flow;        //붐빔 정도

}
