package com.example.finalproject.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AdminReportDto {
    //관리자에서 신고관리 페이지 dto
    private Long reportNum;
    private String reportPlaced;
    private String reportCategory;
    private String reportDetails;
    private String reportDegree;
    private String reportTime;
    private String userName; // 사용자의 이름 추가
}
