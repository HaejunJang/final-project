package com.example.finalproject.service.admin;

import com.example.finalproject.domain.AiModel;
import com.example.finalproject.domain.Feedback;
import com.example.finalproject.domain.Report;
import com.example.finalproject.dto.AdminMainDto;
import com.example.finalproject.repository.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * AdminMainService 클래스는 관리자의 주요 데이터를 가져오는 기능을 제공합니다.
 */
@Service
public class AdminMainService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AdminFeedbackRepository feedbackRepository;

    @Autowired
    private AdminModelRepository modelRepository;

    @Autowired
    private AdminReportRepository reportRepository;

    @Autowired
    private AdminSafeThingRepository safeThingRepository;

    @Autowired
    private AdminDataRepository adminDataRepository;

    /**
     * getMainDatas 메서드는 관리자의 주요 데이터를 가져옵니다.
     *
     * @return AdminMainDto 객체의 리스트
     */
    public List<AdminMainDto> getMainDatas() {
        List<AdminMainDto> mainDtos = new ArrayList<>();

        // 회원 수 가져오기
        Long userCount = userRepository.count();

        // 데이터 수 가져오기
        Long dataCount = adminDataRepository.count();

        // 신고 데이터 가져오기
        List<Report> reports = reportRepository.findAll();
        Long reportNum = reportRepository.count();

        //안전시설 데이터수 가져오기
        Long safeCount = safeThingRepository.count();

        // 피드백 데이터 가져오기
        List<Feedback> feedbacks = feedbackRepository.findAll();
        // 피드백 각 등급별 갯수 계산
        int grade1Count = 0;
        int grade2Count = 0;
        int grade3Count = 0;
        int grade4Count = 0;
        int grade5Count = 0;

        for (Feedback feedback : feedbacks) {
            switch (feedback.getFeedGrade()) {
                case 1:
                    grade1Count++;
                    break;
                case 2:
                    grade2Count++;
                    break;
                case 3:
                    grade3Count++;
                    break;
                case 4:
                    grade4Count++;
                    break;
                case 5:
                    grade5Count++;
                    break;
            }
        }

        // 월별 신고 갯수 세기
        Map<Month, Integer> monthlyReportCount = new HashMap<>();
        for (Report report : reports) {
            LocalDate reportDate = LocalDate.parse(report.getReportTime().substring(0, 10)); // YYYY-MM-DD 형식으로 파싱
            Month month = reportDate.getMonth();
            monthlyReportCount.put(month, monthlyReportCount.getOrDefault(month, 0) + 1);
        }

        // 모델 정보 조회
        Optional<AiModel> activeModelOptional = modelRepository.findByIsActiveTrue();
        if (activeModelOptional.isPresent()) {
            AiModel activeModel = activeModelOptional.get();
            int currentModelNum = activeModel.getModelNum();
            String currentModelName = activeModel.getModelName();
            String imagePath1 = "/ai/models_link/validation_image" + currentModelNum + "/MSE_graph.png";
            String imagePath2 = "/ai/models_link/validation_image" + currentModelNum + "/R-squared_graph.png";
            String imagePath3 = "/ai/models_link/validation_image" + currentModelNum + "/RMSE_graph.png";

            // AdminMainDto 생성 및 설정
            AdminMainDto mainDto = new AdminMainDto();
            mainDto.setModelNum(currentModelNum);
            mainDto.setModelName(currentModelName);
            mainDto.setMseImagePath(imagePath1);
            mainDto.setRImagePath(imagePath2);
            mainDto.setRMSEImagePath(imagePath3);

            // 피드백 정보 설정
            mainDto.setGrade1Count(grade1Count);
            mainDto.setGrade2Count(grade2Count);
            mainDto.setGrade3Count(grade3Count);
            mainDto.setGrade4Count(grade4Count);
            mainDto.setGrade5Count(grade5Count);

            // 데이터 및 회원 정보 설정
            mainDto.setDataNum(dataCount);
            mainDto.setUserNum(userCount);
            mainDto.setReportNum(reportNum);
            mainDto.setSafeNum(safeCount);

            // 월별 신고 갯수 설정
            mainDto.setOneToTwoReportCount(monthlyReportCount.getOrDefault(Month.JANUARY, 0) + monthlyReportCount.getOrDefault(Month.FEBRUARY, 0));
            mainDto.setThreeToFourReportCount(monthlyReportCount.getOrDefault(Month.MARCH, 0) + monthlyReportCount.getOrDefault(Month.APRIL, 0));
            mainDto.setFiveToSixReportCount(monthlyReportCount.getOrDefault(Month.MAY, 0) + monthlyReportCount.getOrDefault(Month.JUNE, 0));
            mainDto.setSevenToEightReportCount(monthlyReportCount.getOrDefault(Month.JULY, 0) + monthlyReportCount.getOrDefault(Month.AUGUST, 0));
            mainDto.setNineToTenReportCount(monthlyReportCount.getOrDefault(Month.SEPTEMBER, 0) + monthlyReportCount.getOrDefault(Month.OCTOBER, 0));
            mainDto.setElevenToTwelve(monthlyReportCount.getOrDefault(Month.NOVEMBER, 0) + monthlyReportCount.getOrDefault(Month.DECEMBER, 0));

            mainDtos.add(mainDto);
        }
        return mainDtos;
    }
}
