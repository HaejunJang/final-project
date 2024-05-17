package com.example.finalproject.controller;

import com.example.finalproject.dto.UserReportDto;
import com.example.finalproject.service.user.UserReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class UserReportController {

    @Autowired
    private UserReportService userReportService;

    //신고 요청
    @PostMapping("/src/users/report")
    public ResponseEntity<String> receiveReport(@RequestBody UserReportDto userReportDto) {
        userReportService.saveReport(userReportDto);
        return ResponseEntity.ok("{\"message\": \"신고가 성공적으로 저장되었습니다.\"}");
    }

    //본인의 신고요청 리스트 조회
    @GetMapping("/src/users/reports")
    public ResponseEntity<List<UserReportDto>> getUserReports() {
        List<UserReportDto> reports = userReportService.getUserReports();
        return new ResponseEntity<>(reports, HttpStatus.OK);
    }

    //본인의 신고요청 리스트 상세 조회
    @GetMapping("/src/users/reports/{reportNum}")
    public ResponseEntity<UserReportDto> getReportByNum(@PathVariable("reportNum") Long reportNum) {
        UserReportDto report = userReportService.getReportByReportNum(reportNum);
        if(report != null) {
            return new ResponseEntity<>(report,HttpStatus.OK);
        }else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
