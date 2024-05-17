package com.example.finalproject.config;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.GenericFilterBean;

@Slf4j
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends GenericFilterBean {

    private final JwtTokenProvider jwtTokenProvider;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws ServletException, java.io.IOException {
        //1. Request Header 에서 JWT 토큰 추출
        String token = resolveToken((HttpServletRequest) request);
        log.info("Token extracted: {}", token);     //제대로 토큰추출하는지 검사

        //2. validateToken 으로 토큰 유효성 검사
        if (token != null && jwtTokenProvider.validateToken(token)) {
            log.info("Token is valid provider");
            //토큰이 유효할 경우 토큰에서 Authentication 객체를 가지고와서 SecurityContext 에 저장
            Authentication authentication = jwtTokenProvider.getAuthentication(token);
            SecurityContextHolder.getContext().setAuthentication(authentication);
        } else {
            log.info("Token is not valid provider");
        }
        chain.doFilter(request, response);
    }

    //Request Header 에서 토큰 정보 추출
    private String resolveToken(HttpServletRequest request) {//HttpRequest 에서 토큰정보 추출 역할
        String bearerToken = request.getHeader("Authorization");    //Authorization 헤더에서
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer")) {//Bearer 접두사로 시작하는 토큰 추출해서 반환
            return bearerToken.substring(7);
        }
        return null;
    }
}
