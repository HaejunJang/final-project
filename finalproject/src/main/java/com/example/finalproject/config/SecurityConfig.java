package com.example.finalproject.config;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfiguration;
import org.springframework.security.config.annotation.web.configurers.CsrfConfigurer;
import org.springframework.security.config.annotation.web.configurers.HttpBasicConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtTokenProvider jwtTokenProvider;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .httpBasic(HttpBasicConfigurer::disable)
                .csrf(CsrfConfigurer::disable)
                .sessionManagement(httpSecuritySessionManagementConfigurer ->
                        httpSecuritySessionManagementConfigurer.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(authorizeRequests ->
                        authorizeRequests
                                //사용자 측
                                .requestMatchers("/src/users/login").permitAll() // "/members/login"에 대한 접근 허용
                                .requestMatchers("/src/users/signup").permitAll()    //회원가입도 모두한테 접근 허용
                                .requestMatchers("/src/users/idCheck/**").permitAll()    //중복id검사 모두한테 접근 허용
                                .requestMatchers("/src/users/**").hasRole("USER") // "/members/test"에 대한 접근은 USER 역할이 있어야 함

                                //관리자 측
                                .requestMatchers("/src/admins/login").permitAll()
                                .requestMatchers("/src/admins/**").hasRole("ADMIN") //여기 url은 ADMIN 권한이 필요함

                                .anyRequest().authenticated() // 그 외 모든 요청은 인증이 필요함
                )
                .addFilterBefore(new JwtAuthenticationFilter(jwtTokenProvider), UsernamePasswordAuthenticationFilter.class)
                //사용자 로그아웃
                .logout(logout -> logout
                        .logoutRequestMatcher(new AntPathRequestMatcher("/src/users/logout"))
                        .logoutSuccessHandler(((request, response, authentication) -> {
                            response.setStatus(HttpServletResponse.SC_OK);
                        }))
                        .deleteCookies("JSESSIONID")    //JSESSIONID 쿠키 삭제
                        .clearAuthentication(true)                          //인증 정보 제거
                        .invalidateHttpSession(true))                     //HTTP 세션 무효화
                //관리자 로그아웃
                .logout(logout -> logout
                        .logoutRequestMatcher(new AntPathRequestMatcher("/src/admins/logout"))
                        .logoutSuccessHandler(((request, response, authentication) -> {
                            response.setStatus(HttpServletResponse.SC_OK);
                        }))
                        .deleteCookies("JSESSIONID")    //JSESSIONID 쿠키 삭제
                        .clearAuthentication(true)                          //인증 정보 제거
                        .invalidateHttpSession(true));                      //HTTP 세션 무효화

        return http.build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration)
            throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }
    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }
}


