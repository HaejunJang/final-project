package com.example.finalproject.service.user;

import com.example.finalproject.domain.Prediction;
import com.example.finalproject.dto.UserFlowResponseDto;
import com.example.finalproject.repository.UserFlowRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class UserFlowService {

    @Autowired
    private UserFlowRepository userFlowRepository;

    public List<UserFlowResponseDto> getUserFlows() {

        // 현재 시간 가져오기
        LocalDateTime now = LocalDateTime.now();

        // 분 단위의 1의 자리 값을 0으로 고정
        now = now.withSecond(0);

        // 만약 분의 1의 자리 값이 1보다 크면 올림하여 처리
        if (now.getMinute() % 10 > 0) {
            now = now.plusMinutes(10 - now.getMinute() % 10);
        }

        now = now.withSecond(0);

        // 시간 형식 지정
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String formattedDateTime = now.format(formatter);

        log.info("서버에서 찍은 시간 확인: " + formattedDateTime);

        List<Prediction> predictions = userFlowRepository.findByTime(formattedDateTime);

        // 가져온 데이터를 UserFlowResponseDto로 변환하여 반환
        return predictions.stream()
                .map(prediction -> new UserFlowResponseDto(prediction.getAddress(), prediction.getFlow()))
                .collect(Collectors.toList());
    }
}
