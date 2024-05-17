package com.example.finalproject.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserSafeThingDto {
    //사용자 안전시설 데이터 전달 dto
    private Long safeNum;       //안전시설 식별번호
    private String safeAddress; //안전시설 주소
    private double safeY;       //위도
    private double safeX;       //경도
    private String safeName;    //안전시설 명
}
