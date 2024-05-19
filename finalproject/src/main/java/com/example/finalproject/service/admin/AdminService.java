package com.example.finalproject.service.admin;

import com.example.finalproject.config.JwtTokenProvider;
import com.example.finalproject.domain.Admin;
import com.example.finalproject.dto.TokenInfo;
import com.example.finalproject.repository.AdminRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Slf4j
public class AdminService {

    private final AdminRepository adminRepository;
    private final JwtTokenProvider jwtTokenProvider;

    @Transactional
    public TokenInfo login(String adminId, String adminPw) {
        Admin admin = adminRepository.findByAdminId(adminId)
                .orElseThrow(()-> new IllegalArgumentException("해당하는 관리자가 없습니다."));

        if (!adminPw.equals(admin.getAdminPw())) {
            log.error("비밀번호가 일치하지 않습니다.");
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }

        //Authentication 객체 생성
        Authentication authentication = new UsernamePasswordAuthenticationToken(adminId, adminPw,
                Arrays.asList(new SimpleGrantedAuthority("ROLE_ADMIN")));
        //토큰 생성
        String token = jwtTokenProvider.generateToken(authentication).getAccessToken();
        String refreshToken = jwtTokenProvider.generateToken(authentication).getRefreshToken();

        return TokenInfo.builder()
                .grantType("Bearer")
                .accessToken(token)
                .refreshToken(refreshToken)
                .build();
    }

    @Transactional
    public void logout() {
        //로그아웃 시 필요한 작업 실행
    }
}
