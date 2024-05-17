package com.example.finalproject.dto;

import lombok.*;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Builder
public class UserSettingResponseDto {
    //회원 맞춤서비스 반환 dto
    private String time;    //시간 
    private String population;    //붐빔정도
}
