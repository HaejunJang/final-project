package com.example.finalproject.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserNoticeDto {    //회원 공지사항 dto
    private Long postNum;
    private String adminName;
    private String postName;
    private String postContent;
    private String postDate;
}
