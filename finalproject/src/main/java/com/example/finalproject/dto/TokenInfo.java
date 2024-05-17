package com.example.finalproject.dto;

import lombok.Builder;
import lombok.Data;

@Builder    //롬복중 하나로 빌더패턴을 자동 생성 
@Data       //롬복중 하나로 게터 세터 투스트링 등 어노테이션 만들어줌
public class TokenInfo {
    private String grantType;
    private String accessToken;
    private String refreshToken;
}