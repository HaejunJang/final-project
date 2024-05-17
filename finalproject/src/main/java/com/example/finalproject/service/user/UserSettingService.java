package com.example.finalproject.service.user;

import com.example.finalproject.domain.Prediction;
import com.example.finalproject.dto.UserSettingRequestDto;
import com.example.finalproject.dto.UserSettingResponseDto;
import com.example.finalproject.repository.UserSettingRepository;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@Slf4j
public class UserSettingService {

    @Autowired
    private UserSettingRepository userSettingRepository;

    // Json파일을 읽어서 매핑 정보를 저장할 맵
    private Map<String, String> addressMappings;

    // 생성자를 통해 JSON파일을 읽어들여 맵에 저장
    public UserSettingService() {
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

    @Transactional
    public UserSettingResponseDto saveService(UserSettingRequestDto userSettingRequestDto) {
        // 플러터 측에서 받아온 주소값 스플릿 과정을 통해 place에 저장하고, 해당 주소로 DB에서 데이터 조회 후 시간과 붐빔 정도를 반환

        // 플러터 측에서 받은 주소 가져와서 값 추출
        String place = userSettingRequestDto.getPlaced();

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
        // Join the address parts with underscores ensuring '-gu' before '-dong'
        String transformedAddress = transformAddress(guPart + "_" + dongPart); // 변환된 주소

        // String transformedAddress = guPart + "_" + dongPart;




        log.info("json변환을 통한 주소 확인: " + transformedAddress);

        if (transformedAddress == null) {
            log.info("해당 주소를 찾지 못했습니다.");
            return null;
        }

        // 주소는 잘 확인 -> 시간을 가져와야 함
        // 오후 2시 30분부터 11시 30분까지의 시간 범위 생성
        LocalDate today = LocalDate.now();
        LocalTime startTime = LocalTime.of(14, 30,00);
        LocalTime endTime = LocalTime.of(23, 30,00);
        LocalDateTime startDateTime = today.atTime(startTime);
        LocalDateTime endDateTime = today.atTime(endTime);
        log.info("조회할 시간 범위: {} ~ {}", startDateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")), endDateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")));

        // 주소 결과가 없을 경우 처리
        if (transformedAddress == null) {
            log.info("해당 주소를 찾지 못했습니다.");
            return null;
        }

        // 주어진 시작 시간부터 종료 시간까지 10분 간격으로 시간 슬롯 리스트 생성
        List<LocalDateTime> timeSlots = Stream.iterate(startDateTime, dt -> dt.plusMinutes(10))
                .limit(ChronoUnit.MINUTES.between(startDateTime, endDateTime) / 10)
                .collect(Collectors.toList());

        Prediction previousData = null;  // 이전 데이터 추적용
        Prediction optimalData = null;   // 최적 데이터 저장용
        double maxDrop = 0.0; // 최대 감소량 추적용

        // 각 시간 슬롯에 대한 데이터 조회 및 처리
        for (LocalDateTime time : timeSlots) {
            // 시간을 문자열로 변환

            String timeString = time.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
            log.info("조회할시간: " + timeString);
            log.info("조회할 주소: " + transformedAddress);
            // 변환된 문자열을 사용하여 데이터 조회
            Prediction currentData = userSettingRepository.findByAddressAndTime(transformedAddress, timeString);

            if (currentData == null) continue;  // 데이터가 없으면 스킵

            // 이전 데이터가 null이 아닌 경우에만 이전 데이터의 플로우 값을 가져옴

            log.info("current 붐빔: "+currentData.getFlow());
            double previousFlowValue = previousData != null ? getFlowValue(previousData.getFlow()) : 0.0;
            double currentFlowValue = getFlowValue(currentData.getFlow());
            double drop = previousFlowValue - currentFlowValue;  // 감소량 계산

            log.info(String.valueOf("currentFlowValue: "+currentFlowValue));
            log.info(String.valueOf("previousFlowValue: "+previousFlowValue));

            // 최대 감소량 업데이트 또는 동일 감소량 시 6시에 가까운 시간 확인
            if (optimalData == null || drop > maxDrop || (drop == maxDrop && Math.abs(time.getHour() - 18) < Math.abs(LocalDateTime.parse(optimalData.getTime(), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")).getHour() - 18))) {
                maxDrop = drop;
                optimalData = currentData;
            }

            previousData = currentData;     //현재 데이터를 이전 데이터로 설정
        }

        // 최적의 데이터 반환
        if (optimalData != null) {
            UserSettingResponseDto responseDto = new UserSettingResponseDto();
            responseDto.setTime(optimalData.getTime());
            responseDto.setPopulation(optimalData.getFlow());
            return responseDto;
        } else {
            log.info("최적의 데이터가 없습니다.");
            return null;
        }
    }

    private double getFlowValue(String flow) {
        switch (flow) {
            case "여유":
                return 1.0;
            case "보통":
                return 2.0;
            case "붐빔":
                return 3.0;
            case "매우붐빔":
                return 4.0;
            default:
                return 0;
        }
    }
}
