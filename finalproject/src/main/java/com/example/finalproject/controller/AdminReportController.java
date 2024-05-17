package com.example.finalproject.controller;

import com.example.finalproject.domain.Report;
import com.example.finalproject.dto.AdminReportDto;
import com.example.finalproject.service.admin.AdminReportService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Slf4j
public class AdminReportController {
    //관리자 신고관리 기능
    @Autowired
    private  AdminReportService reportService;

    //모든 신고 정보 조회
    @GetMapping("/src/admins/reports")
    public ResponseEntity<List<AdminReportDto>> getAllReports() {
        List<AdminReportDto> reportDTOs = reportService.getAllReportsWithUserNames();
        log.info("신고 정보 조회에 성공했습니다.");
        return new ResponseEntity<>(reportDTOs, HttpStatus.OK);
    }

    //단일 신고 정보 조회
    @GetMapping("/src/admins/reports/{reportNum}")
    public ResponseEntity<AdminReportDto> getReportByReportNum(@PathVariable("reportNum") Long reportNum) {
        AdminReportDto reportDTO = reportService.getReportByReportNum(reportNum);
        if (reportDTO != null) {
            log.info("단인 신고 조회에 성공했습니다.");
            return new ResponseEntity<>(reportDTO, HttpStatus.OK);
        } else {
            log.info("단인 신고 조회에 실패했습니다.");
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
