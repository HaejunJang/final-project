package com.example.finalproject.service.user;

import com.example.finalproject.dto.UserRouteResponseDto;
import com.example.finalproject.dto.UserRouteRequestDto;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;

@Service
@Slf4j
public class UserRouteService {

//    //Json파일을 읽어서 매핑정보를 저장할 맵
//    private Map<String, String> addressMappings;
//
//    //생성자를 통해 json파일을 읽어들여 맵에 저장
//    public UserRouteService() {
//        addressMappings = readAddressMappingsFromJson();
//    }
//
//    //Json파일을 읽어서 주소 매핑 정보를 반환하는 함수
//    private Map<String, String> readAddressMappingsFromJson() {
//        try{
//            //학과 서버에서는 json주소 바꿔야 함
//            File file = ResourceUtils.getFile("classpath:location_transform.json");
//            ObjectMapper objectMapper = new ObjectMapper();
//            return objectMapper.readValue(file, new TypeReference<Map<String, String>>() {
//            });
//        } catch (IOException e) {
//            e.printStackTrace();
//            return null;
//        }
//    }
//
//    //주소 변환 메소드
//    private String transformAddress(String originalAddress) {
//        return addressMappings.getOrDefault(originalAddress, originalAddress);
//    }



    public UserRouteResponseDto findRoute(UserRouteRequestDto userRouteRequestDto) {
        //파이썬 코드 실행
        try {
            //플러터에서 받은 주소값 변경
            String start = userRouteRequestDto.getStart();
            String destination = userRouteRequestDto.getDestination();

//            String enResult1 = start.replaceAll(",", " ");
//            // Remove underscores (as it's already in part of the input)
//            String enResult = enResult1.replaceAll("_", " ");
//
//            String enResult3 = destination.replaceAll(",", " ");
//            String enResult2 = enResult3.replaceAll("_", " ");
//
//
//            // Split the address into parts
//            String[] enParts = enResult.split(" ");
//            String[] enParts2 = enResult2.split(" ");
//
//
//            // Reorder parts to ensure '-gu' comes before '-dong'
//            String guPart = "";
//            String dongPart = "";
//            for (String part : enParts) {
//                if (part.contains("-gu")) {
//                    guPart = part;
//                } else if (part.contains("-dong")) {
//                    dongPart = part;
//                }
//            }
//
//            String guPart1 = "";
//            String dongPart1 = "";
//            for (String part : enParts2) {
//                if (part.contains("-gu")) {
//                    guPart1 = part;
//                } else if (part.contains("-dong")) {
//                    dongPart1 = part;
//                }
//            }
//
//            // Join the address parts with underscores ensuring '-gu' before '-dong'
//            String startAddress = guPart + "_" + dongPart;
//            String destinationAddress = guPart1 + "_" + dongPart1;

            log.info("json변환을 통한 출발지 주소 확인: " + start);
            log.info("json변환을 통한 도착지 주소 확인: " + destination);


            //서버에서 시간 가져오는 로직 작성
            LocalDateTime now = LocalDateTime.now();
            String currentTime = now.withSecond(0).format(DateTimeFormatter.ofPattern("yyyy-MM-dd_HH:mm:ss"));
            log.info("현재 시간 확인하기:"+ currentTime);


            //파이썬 스크립트를 실행하기 위한 ProcessBuilder 생성
            ProcessBuilder pb = new ProcessBuilder("python","/home/t24104/v0.9src/ai/route.py",currentTime, start, destination);
            //자바 제공 스트림으로 오류 로그 확인
            pb.redirectErrorStream(true);

            //프로세스 실행
            Process process = pb.start();

            //파이썬 스크립트의 출력을 읽기 위한 BufferedReader 생성
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));

            //결과를 저장할 변수 초기화
            String safeRoute = null;
            long safeDuration = 0;
            String shortestRoute = null;
            long shortestDuration = 0;

            //파이썬 스크립트의 출력을 한 줄씩 읽어서 처리
            String line;
            while ((line = reader.readLine()) != null) {
                // safe route로 시작하는 줄이면 안전 경로 정보를 추출하여 저장
                if (line.startsWith("Safe route:")) {
                    int startIndex = line.indexOf("Safe route:") + "Safe route:".length() + 1; // "Safe route:" 이후의 한 칸 공백까지의 인덱스
                    int endIndex = line.indexOf("with total duration: "); // "with total duration: "의 시작 인덱스
                    safeRoute = line.substring(startIndex, endIndex - 1).trim(); // "Safe route:" 다음의 한 칸 공백부터 "with total duration: " 이전까지 추출
                    safeDuration = Long.parseLong(line.split("with total duration: ")[1].split(" seconds")[0].trim());
                }
                // "Shortest route"로 시작하는 줄이면 최단 경로 정보를 추출하여 저장
                else if (line.startsWith("Shortest route:")) {
                    int startIndex = line.indexOf("Shortest route:") + "Shortest route:".length() + 1; // "Shortest route:" 이후의 한 칸 공백까지의 인덱스
                    int endIndex = line.indexOf("with total duration: "); // "with total duration: "의 시작 인덱스
                    shortestRoute = line.substring(startIndex, endIndex - 1).trim(); // "Shortest route:" 다음의 한 칸 공백부터 "with total duration: " 이전까지 추출
                    shortestDuration = Long.parseLong(line.split("with total duration: ")[1].split(" seconds")[0].trim());
                }
            }

            //프로세스가 종료될 때까지 대기
            int exitCode = process.waitFor();
            System.out.println("Exited with error code " + exitCode);

            //Safe route 값이 없을 경우에 대한 처리
            if(safeRoute == null) {
                safeRoute = "null";
            }


            //결과를 UserRouteReponseDto 객체로 변환하여 반환
            return new UserRouteResponseDto(safeRoute, safeDuration,shortestRoute, shortestDuration);
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
            return null;
        }

    }
}
