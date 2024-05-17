package com.example.finalproject.dto;

import com.example.finalproject.domain.User;
import lombok.*;

@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserDto {
    private Long userNum;
    private String userName;
    private String userId;  //여기서 userPw는 안가져옴
    private String userBirth;
    private String userCall;
    private String userAddress;
    private String userSex;

    static public UserDto toDto(User user) {
        return UserDto.builder()
                .userNum(user.getUserNum())
                .userName(user.getUsername())
                .userId(user.getUserId())
                .userBirth(user.getUserBirth())
                .userCall(user.getUserCall())
                .userAddress(user.getUserAddress())
                .userSex(user.getUserSex())
                .build();
    }
    public User toEntity() {
        return User.builder()
                .userNum(userNum)
                .userName(userName)
                .userId(userId)
                .userBirth(userBirth)
                .userCall(userCall)
                .userAddress(userAddress)
                .userSex(userSex)
                .build();
    }
}
