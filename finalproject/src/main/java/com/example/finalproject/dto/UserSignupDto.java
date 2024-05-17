package com.example.finalproject.dto;

import com.example.finalproject.domain.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserSignupDto {    // user 회원가입 dto

    private String userName;
    private String userId;
    private String userPw;  // 비밀번호를 암호화하지 않음
    private String userBirth;
    private String userCall;
    private String userAddress;
    private String userSex;

    // roles 필드는 기본값으로 초기화하고 변경할 수 없도록 final 키워드 사용
    @Builder.Default
    private final List<String> roles = new ArrayList<>();

    // 비밀번호를 암호화하지 않고 그대로 설정 userService에서 pwEncoder 사용해서 암호화
    public User toEntity() {
        return User.builder()
                .userName(userName)
                .userId(userId)
                .userPw(userPw)
                .userBirth(userBirth)
                .userCall(userCall)
                .userAddress(userAddress)
                .userSex(userSex)
                .build();
    }
}



