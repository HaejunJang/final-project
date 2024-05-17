package com.example.finalproject.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AdminNoticeResponseDto {
    //관리자 공지사항 리스트 조회 dto
    private Long postNum;
    private String postName;
    private String postContent;
    private String postDate;
    private String adminName;
}
