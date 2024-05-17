package com.example.finalproject.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserFlowResponseDto {
    //회원 붐빔정도 반환 dto
    private String address;     //주소
    private String flow;        //붐빔 정도

}
