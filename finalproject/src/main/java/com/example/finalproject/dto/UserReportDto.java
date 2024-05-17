package com.example.finalproject.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserReportDto {
    //회원 신고 요청 dto
    private Long reportNum;       //신고 식별번호
    private String reportPlaced;    //신고 위치
    private String reportCategory;  //신고 카테고리
    private String reportDetails;   //신고 상세정보
    private String reportDegree;    //신고 긴급정도
    private String reportTime;      //신고 시각
}
