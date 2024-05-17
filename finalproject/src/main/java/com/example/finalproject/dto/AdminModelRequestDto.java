package com.example.finalproject.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AdminModelRequestDto {
    private Integer batchSize;  //미니 배치 크기
    private Integer epoch;      //에포크 수
}
