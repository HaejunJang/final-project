package com.example.finalproject.dto;

import lombok.*;

@Data   //getter, setter 역할포함
public class UserLoginRequestDto {
    private String userId;
    private String userPw;
}
