//package com.example.finalproject;
//
//import com.example.finalproject.dto.UserDto;
//import com.example.finalproject.dto.UserSignupDto;
//import com.example.finalproject.service.user.UserService;
//import lombok.extern.slf4j.Slf4j;
//import org.assertj.core.api.Assertions;
//import org.junit.jupiter.api.AfterEach;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.boot.test.web.client.TestRestTemplate;
//import org.springframework.boot.test.web.server.LocalServerPort;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//
//@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
//@Slf4j
//public class UserControllerTest {
//
//    @Autowired
//    DatabaseCleanUp databaseCleanUp;
//    @Autowired
//    UserService userService;
//    @Autowired
//    TestRestTemplate testRestTemplate;
//    @LocalServerPort
//    int randomServerPort;
//
//    private UserSignupDto signupDto;
//
//    @BeforeEach
//    void beforeEach() {
//        //User 회원 가입
//        signupDto = UserSignupDto.builder()
//                .userName("장해준")
//                .userId("wnsdl03")
//                .userPw("12345")
//                .userBirth("2000-10-22")
//                .userCall("010-202-1021")
//                .userAddress("서울시 도봉구")
//                .userSex("남자")
//                .build();
//    }
//    @AfterEach
//    void afterEach() {
//        databaseCleanUp.truncateAllEntity();
//    }
//
//    @Test
//    public void signupTest() {
//        //API 요청 설정
//        String url = "http://localhost:" + randomServerPort +"/members/singup";
//        ResponseEntity<UserDto> responseEntity = testRestTemplate.postForEntity(url,signupDto,UserDto.class);
//
//        //응답 검증
//        Assertions.assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
//        UserDto savedUserDto = responseEntity.getBody();
//        Assertions.assertThat(savedUserDto.getUserName()).isEqualTo(signupDto.getUserName());
//        Assertions.assertThat(savedUserDto.getUserId()).isEqualTo(signupDto.getUserId());
//    }
//}
