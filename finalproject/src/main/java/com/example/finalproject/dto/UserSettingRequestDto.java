package com.example.finalproject.dto;

import lombok.*;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserSettingRequestDto {
    //회원 맞춤서비스 요청 dto
    private String placed;  //장소
}
