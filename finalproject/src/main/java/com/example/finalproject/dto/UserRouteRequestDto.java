package com.example.finalproject.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserRouteRequestDto {
    //사용자 경로 요청 dto
    private String start;           //출발지
    private String destination;     //목적지
}
