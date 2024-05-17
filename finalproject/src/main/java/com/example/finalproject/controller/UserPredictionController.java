package com.example.finalproject.controller;

import com.example.finalproject.dto.UserPredictionRequestDto;
import com.example.finalproject.dto.UserPredictionResponseDto;
import com.example.finalproject.service.user.UserPredictionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class UserPredictionController {
    //회원 예측하기 컨트롤러

    @Autowired
    private UserPredictionService userPredictionService;

//    플러터 측에서 요청시 예측정보 반환
@PostMapping("/src/users/prediction")
public ResponseEntity<?> getUserPrediction(@RequestBody UserPredictionRequestDto userPredictionRequestDto) {
    try {
        List<UserPredictionResponseDto> responseDtos = userPredictionService.getUserPrediction(userPredictionRequestDto);
        if (!responseDtos.isEmpty()) {
            return ResponseEntity.ok(responseDtos);
        } else {
            String errorMessage = "{\"message\": \"해당 시간에 대한 예측 데이터가 없습니다.\"}";
            return ResponseEntity.status(HttpStatus.NOT_FOUND).contentType(MediaType.APPLICATION_JSON).body(errorMessage);
        }
    } catch (Exception e) { //학과서버에서 locationTransform.json안옮기면 이 오류남
        String errorMessage = "{\"message\": \"예측 정보를 가져오는 중에 오류가 발생했습니다.\"}";
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).contentType(MediaType.APPLICATION_JSON).body(errorMessage);
    }
}
}

