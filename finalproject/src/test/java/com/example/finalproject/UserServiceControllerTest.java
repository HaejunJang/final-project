//package com.example.finalproject;
//
//import com.example.finalproject.config.JwtTokenProvider;
//import com.example.finalproject.dto.UserLoginRequestDto;
//import com.example.finalproject.service.user.UserService;
//import org.assertj.core.api.Assertions;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.boot.test.web.client.TestRestTemplate;
//import org.springframework.http.*;
//import org.springframework.test.context.ActiveProfiles;
//
//@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
//@ActiveProfiles("test")
//public class UserServiceControllerTest {
//
//    @Autowired
//    private UserService userService;
//
//    @Autowired
//    private TestRestTemplate testRestTemplate;
//
//    @Autowired
//    private JwtTokenProvider jwtTokenProvider;
//
//    @Test
//    public void loginTest() {
//        // 로그인 요청을 수행하고 토큰을 받아옴
//        UserLoginRequestDto loginRequestDto = UserLoginRequestDto.builder()
//                .userId("wnsdl03")
//                .userPw("12345")
//                .build();
//
//        ResponseEntity<String> responseEntity = testRestTemplate.postForEntity("/members/login", loginRequestDto, String.class);
//        Assertions.assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
//
//        // 로그인 후 토큰을 헤더에 설정하여 API 요청
//        HttpHeaders httpHeaders = new HttpHeaders();
//        httpHeaders.setBearerAuth(responseEntity.getBody());
//        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
//
//        HttpEntity<String> httpEntity = new HttpEntity<>(httpHeaders);
//        ResponseEntity<String> testResponseEntity = testRestTemplate.exchange("/members/test", HttpMethod.POST, httpEntity, String.class);
//
//        Assertions.assertThat(testResponseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
//        Assertions.assertThat(testResponseEntity.getBody()).isEqualTo("success");
//    }
//}
