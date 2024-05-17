package com.example.finalproject.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserSettingListDto {
    //회원 맞춤서비스 리스트 dto
    private Long id;        //식별번호
    private String placed;  //장소
    private String time;    //시간
}
