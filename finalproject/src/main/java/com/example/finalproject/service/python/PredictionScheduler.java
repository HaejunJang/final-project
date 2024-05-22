package com.example.finalproject.service.python;

import com.example.finalproject.domain.Prediction;
import com.example.finalproject.repository.UserPredictionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Component
public class PredictionScheduler {
    //파이썬 돌려서 유동인구 예측 데이터 채우기
    //최초 실행시간은 7/2일까지 db에 들어가있으므로 2주전인 6/18부터 2주치의 데이터가 2주간격으로 데이터가 채워짐

    @Autowired
    private UserPredictionRepository predictionRepository;

    // 최초 실행일을 설정합니다.
    private final LocalDateTime currentDateTime = LocalDateTime.of(2024, 6, 18, 0, 0);

    // 2주 간격으로 실행할 것이므로 주 단위로 설정합니다.
    private final int numWeeks = 2;

    // 10분 간격으로 총 144회 실행할 것이므로 총 실행 횟수를 설정합니다.
    private final int numIncrements = 144;

    // 10분마다 실행합니다.
    @Scheduled(fixedDelay = 600000)
    public void executePythonScript() {
        // 현재 서버의 날짜와 시간을 가져옵니다.
        LocalDateTime serverDateTime = LocalDateTime.now();

        // 최초 실행일 이후의 첫 실행을 확인합니다.
        if (serverDateTime.isEqual(currentDateTime.plusDays(1))) {
            // 최초 실행일 이후부터 파이썬 코드를 실행합니다.
            for (int i = 0; i < numWeeks; i++) {
                LocalDateTime startDate = currentDateTime.plusDays(1).plusWeeks(i * 2); // 현재 날짜 기준으로 2주 간격으로 실행
                executePythonForWeek(startDate);
            }
        }
    }

    // 한 주 단위로 파이썬 코드를 실행하는 메서드입니다.
    private void executePythonForWeek(LocalDateTime startDate) {
        // 한 주에 대한 실행 횟수만큼 파이썬 코드를 실행합니다.
        for (int i = 0; i < numIncrements; i++) {
            LocalDateTime currentTime = startDate.plusMinutes(i * 10).plusDays(1); // 현재 시간에 10분마다 실행하되 하루를 더합니다.
            runPythonScript(currentTime);
        }
    }

    // 파이썬 스크립트를 실행하는 메서드입니다.
    private void runPythonScript(LocalDateTime currentTime) {
        // 현재 시간을 문자열로 변환합니다. +1일을 해줍니다.
        String inputTimeStr = currentTime.plusDays(1).format(DateTimeFormatter.ofPattern("yyyy-MM-dd_HH:mm:ss"));
        try {
            // 파이썬 스크립트를 실행하는 프로세스를 빌드합니다.
            ProcessBuilder processBuilder = new ProcessBuilder("python", "/home/t24104/v0.9src/ai/scatter.py", inputTimeStr, "model", "scaler");
            Process process = processBuilder.start();

            // 파이썬 스크립트의 출력을 읽어옵니다.
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line;
            while ((line = reader.readLine()) != null) {
                // 로그 또는 경고 메시지인지 확인합니다.
                if (isLogOrWarningMessage(line)) {
                    // 로그 또는 경고 메시지는 DB에 저장하지 않습니다.
                    continue;
                }
                // 시간 문자열에서 '_'를 제거하여 올바른 형식으로 변환합니다.
                String[] parts = line.split(":");
                String[] timeParts = parts[2].trim().split("_");
                String formattedTime = timeParts[0] + " " + timeParts[1]; // 시간과 일자 사이에 공백을 추가하여 올바른 형식으로 변환합니다.
                String address = parts[0].trim();
                String flow = parts[1].trim();
                Prediction prediction = new Prediction();
                prediction.setAddress(address);
                prediction.setTime(formattedTime);
                prediction.setFlow(flow);
                predictionRepository.save(prediction);
            }

            // 파이썬 스크립트의 실행이 종료될 때까지 대기합니다.
            process.waitFor();
            process.destroy();
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    // 로그 또는 경고 메시지인지 확인하는 메서드입니다.
    private boolean isLogOrWarningMessage(String line) {
        // 로그 또는 경고 메시지의 패턴을 확인하여 true 또는 false를 반환합니다.
        // 여기에서는 간단히 로그 또는 경고 메시지가 포함되어 있으면 true를 반환하도록 구현합니다.
        return line.contains("E ") || line.contains("W ");
    }
}
