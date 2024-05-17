package com.example.finalproject.dto;

import lombok.*;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class AdminUserDto {
    //관리자 회원 조회dto
    private Long userNum;
    private String userName;
    private String userId;
    private String userAddress;
    private String userBirth;
    private String userSex;
    private String userCall;
}
