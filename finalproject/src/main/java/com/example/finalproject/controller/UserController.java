package com.example.finalproject.controller;

import com.example.finalproject.domain.User;
import com.example.finalproject.dto.*;
import com.example.finalproject.service.user.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@RequiredArgsConstructor
@RestController
public class UserController {

    @Autowired
    private UserService userService;

    //회원가입
    @PostMapping("/src/users/signup")
    public ResponseEntity<UserDto> signup(@RequestBody UserSignupDto signupDto){
        UserDto savedUserDto = userService.signup(signupDto);
        return ResponseEntity.ok(savedUserDto);
    }
    //중복 id 확인
    @GetMapping("/src/users/idCheck/{userId}")
    public ResponseEntity<Map<String, String>> checkUserId(@PathVariable("userId") String userId) {
        boolean isDuplicate = userService.checkUserId(userId);
        Map<String, String> response = new HashMap<>();
        if (isDuplicate) {
            response.put("message", "중복된 아이디가 있습니다.");
            return ResponseEntity.badRequest().body(response);
        } else {
            response.put("message", "사용 가능한 아이디 입니다.");
            return ResponseEntity.ok(response);
        }
    }
    //로그인
    @PostMapping("/src/users/login")
    public ResponseEntity<Map<String, Object>> login(@RequestBody UserLoginRequestDto userLoginRequestDto) {
        String userId = userLoginRequestDto.getUserId();
        String userPw = userLoginRequestDto.getUserPw();
        Map<String, Object> response = userService.login(userId, userPw);
        return ResponseEntity.ok(response);
    }

    //로그아웃  시큐리티에서는 POST 방식을 사용한다
    @PostMapping("/src/users/logout")
    public ResponseEntity<String> userLogout() {
        SecurityContextHolder.clearContext();
        return ResponseEntity.ok().body("{\"message\": \"Logout success\"}");
    }

    //회원 탈퇴 탈퇴도 토큰검사 진행
    @DeleteMapping("/src/users/userDelete/{userId}")
    public ResponseEntity<Object> deleteUser(@PathVariable("userId") String userId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!(authentication instanceof AnonymousAuthenticationToken)) {
            userService.deleteUser(userId);
            log.info("회원 ID: " + userId + " 탈퇴 성공");
            return ResponseEntity.ok().body("{\"message\": \"회원 ID: " + userId + " 탈퇴 성공\"}");
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("{\"message\": \"로그인이 필요합니다.\"}");
        }
    }

    //비밀번호 확인 요청
    @PostMapping("/src/users/pwCheck")
    public ResponseEntity<String> checkPassword(@RequestBody UserPwCheckDto userPwCheckDto) {

        boolean isPasswordCorrect = userService.checkPassword(userPwCheckDto);
        if(isPasswordCorrect) {
            return ResponseEntity.ok().body("{\"message\": \"본인확인에 성공했습니다.\"}");
        } else {
            return ResponseEntity.badRequest().body("{\"message\": \"본인확인에 실패했습니다.\"}");
        }
    }

    //비밀번호 변경 요청
    @PostMapping("/src/users/pwChange")
    public ResponseEntity<String> changePw(@RequestBody UserPwChangeDto dto) {
        //현재 인증된 사용자의 ID 가져오기
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userId = authentication.getName();

        //새로운 비밀번호로 변경 요청 처리
        userService.changePw(userId,dto.getUserPw());

        return ResponseEntity.ok().body("{\"message\": \"비밀번호가 성공적으로 변경되었습니다.\"}");
    }

    //프로필 정보 수정 정보 요청
    @GetMapping("/src/users/update")
    public ResponseEntity<UserUpdateDto> getUserInfo() {
        UserUpdateDto userUpdateDto = userService.getUserInfo();
        return ResponseEntity.ok(userUpdateDto);
    }

    //프로필 정보 수정
    @PutMapping("/src/users/update")
    public ResponseEntity<String> updateUser(@RequestBody UserUpdateDto userUpdateDto){
        userService.updateUser(userUpdateDto);
        return ResponseEntity.ok().body("{\"message\": \"회원 정보가 성공적으로 수정되었습니다.\"}");
    }

}
