package com.example.finalproject.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AdminSafeDataRequestDto {
    //관리자가 데이터 요청시 dto
    private Long safeNum;       //안전시설 구분번호
    private String safeAddress; //안전시설 주소
    private double safeY;   //안전시설 위도값
    private double safeX;   //안전시설 경도값
}
