package com.example.finalproject.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AdminSafeDataDto {
    //관리자 안전시설 등록
    //엑셀 파일에서 추출한 데이터를 담을 dto
    private String safeAddress; //안전시설 주소
    private double safeY;   //안전시설 위도값
    private double safeX;   //안전시설 경도값
    private int safeTypeNum;//안전시설 구분
}
