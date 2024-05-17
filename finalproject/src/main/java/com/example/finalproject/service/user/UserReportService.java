package com.example.finalproject.service.user;

import com.example.finalproject.domain.Report;
import com.example.finalproject.domain.User;
import com.example.finalproject.dto.UserReportDto;
import com.example.finalproject.repository.UserReportRepository;
import com.example.finalproject.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserReportService {
    //회원 신고하기, 신고내역 조회, 상세조회
    //추후에 dto로 바꾸는 중복작업 함수로 바꿔서 리펙토링
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserReportRepository userReportRepository;

    @Transactional
    public void saveReport(UserReportDto userReportDto) {
        //현재 사용자 정보 가져오기
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userId = authentication.getName();

        //userId로 사용자 조회
        User user = userRepository.findByUserId(userId)
                .orElseThrow(()->new IllegalArgumentException("해당하는 사용자가 없습니다."));

        //현재 시간 기록
        LocalDateTime now = LocalDateTime.now();

        //시간 형식 지정
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String formattedDateTime = now.format(formatter);

        //신고 정보 저장
        Report report = Report.builder()
                .user(user)
                .reportPlaced(userReportDto.getReportPlaced())
                .reportCategory(userReportDto.getReportCategory())
                .reportDetails(userReportDto.getReportDetails())
                .reportDegree(userReportDto.getReportDegree())
                .reportTime(userReportDto.getReportTime())
                .reportTime(formattedDateTime)
                .build();

        //신고 정보 저장
        userReportRepository.save(report);
    }

    public List<UserReportDto> getUserReports() {
        //현재 사용자 정보 가져오기
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userId = authentication.getName();

        // userId로 사용자 조회
        User user = userRepository.findByUserId(userId)
                .orElseThrow(() -> new IllegalArgumentException("해당하는 사용자가 없습니다."));

        //사용자의 신고 내역 조회
        List<Report> reports = userReportRepository.findByUser(user);

        //조회된 신고 내역을 DTO로 변환
        return reports.stream()
                .map(report -> UserReportDto.builder()
                        .reportNum(report.getReportNum())
                        .reportPlaced(report.getReportPlaced())
                        .reportCategory(report.getReportCategory())
                        .reportDetails(report.getReportDetails())
                        .reportDegree(report.getReportDegree())
                        .reportTime(report.getReportTime())
                        .build())
                .collect(Collectors.toList());
    }

    public UserReportDto getReportByReportNum(Long reportNum) {
        Report report = userReportRepository.findById(reportNum).orElse(null);
        if(report != null){
            UserReportDto dto = new UserReportDto();
            dto.setReportNum(report.getReportNum());
            dto.setReportPlaced(report.getReportPlaced());
            dto.setReportCategory(report.getReportCategory());
            dto.setReportDetails(report.getReportDetails());
            dto.setReportDegree(report.getReportDegree());
            dto.setReportTime(report.getReportTime());
            return dto;
        }
        else {
            return null;
        }
    }
}
