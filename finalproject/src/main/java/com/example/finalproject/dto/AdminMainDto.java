package com.example.finalproject.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AdminMainDto {
    //관리자 메인화면 dto

    //월별 신고 갯수
    private int OneToTwoReportCount;  //1~2
    private int ThreeToFourReportCount;//34
    private int FiveToSixReportCount;//56
    private int SevenToEightReportCount;//78
    private int NineToTenReportCount;//9 10 
    private int ElevenToTwelve;//11 12
            
    private int grade1Count;    //피드백별 갯수
    private int grade2Count;
    private int grade3Count;
    private int grade4Count;
    private int grade5Count;

    private Long reportNum; // 신고 갯수
    private Long userNum;   //회원 갯수
    private Long safeNum; // 안전시설 갯수
    private Long dataNum;   //데이터 갯수            o

    private int modelNum; //현재 모델 번호
    private String modelName; // 모델 이름  -> 생성일자 서버에서 시간찍기
    private String MseImagePath;    //이미지 경로
    private String RImagePath;
    private String RMSEImagePath;


}
