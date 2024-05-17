package com.example.finalproject.service.user;

import com.example.finalproject.config.JwtTokenProvider;
import com.example.finalproject.domain.User;
import com.example.finalproject.dto.*;
import com.example.finalproject.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Slf4j
public class UserService {

    //회원 로그인, 로그아웃, 회원가입
    private final UserRepository userRepository;
    private final JwtTokenProvider jwtTokenProvider;
    private final PasswordEncoder passwordEncoder;

public Map<String, Object> login(String userId, String userPw) {
    User user = userRepository.findByUserId(userId)
            .orElseThrow(() -> new IllegalArgumentException("해당하는 사용자가 없습니다."));

    if (!passwordEncoder.matches(userPw, user.getUserPw())) {
        log.error("Password does not match");
        throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
    }

    // Authentication 객체 생성 userId를 포함해서 구별
    Authentication authentication = new UsernamePasswordAuthenticationToken(userId, userPw);
    String token = jwtTokenProvider.generateToken(authentication).getAccessToken();
    String refreshToken = jwtTokenProvider.generateToken(authentication).getRefreshToken();

    Map<String, Object> response = new HashMap<>();
    response.put("userId", userId);     //토큰에 userId 값을 포함시킴
    response.put("userName", user.getUsername()); // userName 추가
    response.put("userBirth", user.getUserBirth()); // userBirth 추가
    response.put("userSex", user.getUserSex()); // userSex 추가
    response.put("tokenInfo", TokenInfo.builder()
            .grantType("Bearer")
            .accessToken(token)
            .refreshToken(refreshToken)
            .build());

    return response;
}

    @Transactional
    public UserDto signup(UserSignupDto signupDto) {
        if (userRepository.existsByUserId(signupDto.getUserId())) {
            throw new IllegalArgumentException("이미 사용중인 id 입니다.");
        }

        String encodedPassword = passwordEncoder.encode(signupDto.getUserPw());
        List<String> roles = new ArrayList<>();
        roles.add("USER");

        User user = signupDto.toEntity();
        user.setUserPw(encodedPassword);
        user.setRoles(roles);

        User savedUser = userRepository.save(user);

        return UserDto.toDto(savedUser);
    }

    @Transactional
    public void deleteUser(String userId) {
        //userId로 사용자 조회
        User user = userRepository.findByUserId(userId)
                .orElseThrow(() -> new IllegalArgumentException("유저 id를 찾지 못했습니다"));

        //존재하면 삭제
        userRepository.delete(user);
    }
    public boolean checkUserId(String userId) {
        return userRepository.existsByUserId(userId);
    }

    //비밀번호 확인 
    public boolean checkPassword(UserPwCheckDto userPwCheckDto) {
        String userPw = userPwCheckDto.getUserPw();
    
        //현재 인증된 사용자의 ID 가져오기
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userId = authentication.getName();   //메소드 이름은 getName()이지만 토큰을 만들때 userId 값을 포함시킴

        //사용자의 비밀번호 확인
        User user = userRepository.findByUserId(userId)
                .orElseThrow(()->new IllegalArgumentException("해당하는 사용자가 없습니다."));
            return passwordEncoder.matches(userPw,user.getUserPw());
    }

    //비밀번호 변경
    @Transactional
    public void changePw(String userId, String newUserPw) {
        //현재 인증된 사용자의 id로 사용자를 찾기
        User user = userRepository.findByUserId(userId)
                .orElseThrow(()->new IllegalArgumentException("사용자를 찾을 수 없습니다."));

        //새로운 비밀번호를 암호화
        String encodedPw = passwordEncoder.encode(newUserPw);

        //사용자의 비밀번호 변경
        user.setUserPw(encodedPw);

        //변경된 비밀번호로 db 저장
        userRepository.save(user);
    }

    //기존의 회원 정보 가져오기
    public UserUpdateDto getUserInfo() {
    //현재 사용자 정보 가져오기
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userId = authentication.getName();

        //userId로 회원 조회
        User user = userRepository.findByUserId(userId)
                .orElseThrow(()->new IllegalArgumentException("해당하는 사용자가 없습니다."));

        //조회된 사용자 정보를 UserDto로 변환하여 반환
        return UserUpdateDto.toDto(user);
    }
    
    //회원 정보 수정
    @Transactional
    public void updateUser(UserUpdateDto userUpdateDto) {
    //현재 사용자 정보 가져오기
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userId = authentication.getName();

        //userId로 사용자 조회
        User user = userRepository.findByUserId(userId)
                .orElseThrow(()->new IllegalArgumentException("해당하는 사용자가 없습니다."));

        //사용자 정보 업데이트
        user.setUserName(userUpdateDto.getUserName());
        user.setUserCall(userUpdateDto.getUserCall());
        user.setUserBirth(userUpdateDto.getUserBirth());
        user.setUserAddress(userUpdateDto.getUserAddress());
        user.setUserSex(userUpdateDto.getUserSex());

        //변경된 정보 저장
        userRepository.save(user);
    }
}
