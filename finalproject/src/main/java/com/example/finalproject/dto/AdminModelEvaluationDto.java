package com.example.finalproject.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AdminModelEvaluationDto {
    private int modelNum;       //모델 번호
    private String modelName;   //모델 이름
    private String imagePath_MSE;   //이미지 경로
    private String imagePath_R;   //이미지 경로
    private String imagePath_RMSE;   //이미지 경로

}
