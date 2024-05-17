package com.example.finalproject.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AdminFeedbackDto {
    private Long feedNum; // 회원 후기 식별번호, PK
    private int feedGrade; // 회원 별점
    private String feedContent;    //회원 피드백
    private String userName;    //회원 이름
}
