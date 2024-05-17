package com.example.finalproject.service.user;

import com.example.finalproject.domain.Prediction;
import com.example.finalproject.dto.UserPredictionRequestDto;
import com.example.finalproject.dto.UserPredictionResponseDto;
import com.example.finalproject.repository.UserPredictionRepository;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
public class UserPredictionService {

    @Autowired
    private UserPredictionRepository userPredictionRepository;

    //Json파일을 읽어서 매핑정보를 저장할 맵
    private Map<String, String> addressMappings;

    //생성자를 통해 json파일을 읽어들여 맵에 저장
    public UserPredictionService() {
        addressMappings = readAddressMappingsFromJson();
    }

    // json파일을 읽어서 주소 매핑 정보를 반환하는 함수
    private Map<String, String> readAddressMappingsFromJson() {
        try {
            // 학과 서버에선느 json주소 바꿔야 함
            File file = ResourceUtils.getFile("classpath:location_transform.json");
            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.readValue(file, new TypeReference<Map<String, String>>() {
            });
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    // 주소 변환 메소드
    private String transformAddress(String originalAddress) {
        return addressMappings.getOrDefault(originalAddress, originalAddress);
    }

    public List<UserPredictionResponseDto> getUserPrediction(UserPredictionRequestDto userPredictionRequestDto) {
        //플러터 측에서 받은 주소 가져와서 값 추출
        String place = userPredictionRequestDto.getAddress();

        String enResult1 = place.replaceAll(",", " ");
        // Remove underscores (as it's already in part of the input)
        String enResult = enResult1.replaceAll("_", " ");

        // Split the address into parts
        String[] enParts = enResult.split(" ");

        // Reorder parts to ensure '-gu' comes before '-dong'
        String guPart = "";
        String dongPart = "";
        for (String part : enParts) {
            if (part.contains("-gu")) {
                guPart = part;
            } else if (part.contains("-dong")) {
                dongPart = part;
            }
        }

        // Join the address parts with underscores ensuring '-gu' before '-dong'
        String transformedAddress = guPart + "_" + dongPart;
        log.info("json변환을 통한 주소 확인: " + transformedAddress);

        //여기까지가 주소처리
        //이제 시간 형식 맞추기
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        LocalDateTime time = LocalDateTime.parse(userPredictionRequestDto.getTime(), formatter);

        time= time.withSecond(0);

        // 10분 단위로 반올림
        int minute = time.getMinute();
        if (minute % 10 != 0) {
            minute = (minute / 10 + 1) * 10;
            time = time.withMinute(minute);
        }

        log.info("반환된 시간:"+time.toString());
        String timeString = time.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        log.info("반환된 시간:"+timeString);



        // 잘못된 주소인 경우 처리
        if (!addressMappings.containsKey(transformedAddress)) {
            log.warn("잘못된 주소입니다.");
            return new ArrayList<>(); // 빈 리스트 반환
        }

        //시간을 통해서 데이터 조회
        List<Prediction> predictions = userPredictionRepository.findByTime(timeString);

        // 예측 데이터가 없을 경우 처리
        if (predictions.isEmpty()) {
            // 예외 처리 또는 기본값 설정 등을 여기에 추가할 수 있어요.
            log.warn("해당 주소와 시간에 대한 예측 데이터가 없습니다.");
            return new ArrayList<>(); // 예외 처리나 기본값 설정에 따라 다르게 처리 가능
        }
        // 예측 데이터를 UserPredictionResponseDto 리스트로 변환
        List<UserPredictionResponseDto> responseDtos = new ArrayList<>();
        for (Prediction prediction : predictions) {
            UserPredictionResponseDto responseDto = new UserPredictionResponseDto();
            responseDto.setAddress(prediction.getAddress());
            responseDto.setFlow(prediction.getFlow());
            responseDtos.add(responseDto);
        }

        return responseDtos;
    }
}