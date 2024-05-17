package com.example.finalproject.dto;

import com.example.finalproject.domain.User;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserUpdateDto {
    //회원 정보 수정 dto
    private String userName;
    private String userCall;
    private String userBirth;
    private String userAddress;
    private String userSex;

    public static UserUpdateDto toDto(User user) {
        return UserUpdateDto.builder()
                .userName(user.getUsername())
                .userCall(user.getUserCall())
                .userBirth(user.getUserBirth())
                .userAddress(user.getUserAddress())
                .userSex(user.getUserSex())
                .build();
    }

    public User toEntity() {
        return User.builder()
                .userName(userName)
                .userCall(userCall)
                .userBirth(userBirth)
                .userAddress(userAddress)
                .userSex(userSex)
                .build();
    }
}
