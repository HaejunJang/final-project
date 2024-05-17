package com.example.finalproject.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AdminNoticeRequestDto {
    //관리자 공지사항 등록 dto
    private String postName;    //공지사항 제목
    private String postContent; //공지사항 내용
}
