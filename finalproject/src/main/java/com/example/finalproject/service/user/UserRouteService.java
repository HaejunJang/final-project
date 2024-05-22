package com.example.finalproject.service.user;

import com.example.finalproject.dto.UserRouteResponseDto;
import com.example.finalproject.dto.UserRouteRequestDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Service
@Slf4j
public class UserRouteService {

    public UserRouteResponseDto findRoute(UserRouteRequestDto userRouteRequestDto) {
        try {
            // 플러터에서 받은 주소값
            String startRequest = userRouteRequestDto.getStart();
            String destinationRequest = userRouteRequestDto.getDestination();

            log.info("들어온 출발지: "+startRequest);
            log.info("들어온 도착지:"+destinationRequest);

            //영어 주소일때 사용
            // String start = userRouteRequestDto.getStart().replaceAll(",", "").replaceAll(" ", ",");
            // String destination = userRouteRequestDto.getDestination().replaceAll(",","").replaceAll(" ",",");

            // log.info("출발지 주소 확인: " + start);
            // log.info("도착지 주소 확인: " + destination);

            // 서버에서 현재 시간 가져오기
            // 서버에서 현재 시간 가져오기
            LocalDateTime now = LocalDateTime.now();
            int minute = now.getMinute();
            int roundedMinute = (minute / 10) * 10; // 분의 1의 자리를 내림 처리
            now = now.withMinute(roundedMinute).withSecond(0);
            String currentTime = now.format(DateTimeFormatter.ofPattern("yyyy-MM-dd_HH:mm:ss"));
            log.info("현재 시간 확인: " + currentTime);


            // 파이썬 스크립트를 실행하기 위한 ProcessBuilder 생성
            ProcessBuilder pb = new ProcessBuilder(
                    "python",
                    "/home/t24104/v0.9src/ai/route.py",
                    currentTime,
                    "\"" + startRequest + "\"",
                    "\"" + destinationRequest + "\"",
                    "model",
                    "scaler"
            );

            log.info("Executing command: " + String.join(" ", pb.command()));
            // 자바 제공 스트림으로 오류 로그 확인
            pb.redirectErrorStream(true);

            // 프로세스 실행
            Process process = pb.start();

            // 파이썬 스크립트의 출력을 읽기 위한 BufferedReader 생성
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            BufferedReader errorReader = new BufferedReader(new InputStreamReader(process.getErrorStream()));

            // 결과를 저장할 변수 초기화
            String safeRoute = null;
            long safeDuration = 0;
            String shortestRoute = null;
            long shortestDuration = 0;

            // 파이썬 스크립트의 출력을 한 줄씩 읽어서 처리
            String line;
            while ((line = reader.readLine()) != null) {
                log.info("11111111111t: " + line);
                // safe route로 시작하는 줄이면 안전 경로 정보를 추출하여 저장
                if (line.startsWith("Safe route:")) {
                    int startIndex = line.indexOf("Safe route:") + "Safe route:".length() + 1;
                    int endIndex = line.indexOf("with total duration: ");
                    safeRoute = line.substring(startIndex, endIndex).trim();
                    safeDuration = Long.parseLong(line.split("with total duration: ")[1].split(" seconds")[0].trim());
                }
                // Shortest route로 시작하는 줄이면 최단 경로 정보를 추출하여 저장
                else if (line.startsWith("Shortest route:")) {
                    int startIndex = line.indexOf("Shortest route:") + "Shortest route:".length() + 1;
                    int endIndex = line.indexOf("with total duration: ");
                    shortestRoute = line.substring(startIndex, endIndex).trim();
                    shortestDuration = Long.parseLong(line.split("with total duration: ")[1].split(" seconds")[0].trim());
                }
            }

            // 에러 스트림도 읽어서 로깅
            while ((line = errorReader.readLine()) != null) {
                log.error("Python error output: " + line);
            }

            // 프로세스가 종료될 때까지 대기 (타임아웃 설정)
            int exitCode = process.waitFor();
            log.info("Exited with error code " + exitCode);

            // Safe route 값이 없을 경우에 대한 처리
            if (safeRoute == null || safeRoute.equals("None")) {
                safeRoute = "null";
            }

            // 결과를 UserRouteResponseDto 객체로 변환하여 반환
            return new UserRouteResponseDto(safeRoute, safeDuration, shortestRoute, shortestDuration);
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
            return null;
        }
    }
}
