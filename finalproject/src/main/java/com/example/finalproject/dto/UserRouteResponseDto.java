package com.example.finalproject.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserRouteResponseDto {
    private String safePoly;            // 안전 경로의 폴리노미얼 경로
    private Long safeTime;              // 안전 경로의 소요 시간 (초)
    private String shortestPoly;        // 최단 경로의 폴리노미얼 경로
    private Long shortestTime;          // 최단 경로의 소요 시간 (초)
}
