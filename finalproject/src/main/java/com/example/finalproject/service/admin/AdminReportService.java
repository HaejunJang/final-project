package com.example.finalproject.service.admin;

import com.example.finalproject.domain.Report;
import com.example.finalproject.dto.AdminReportDto;
import com.example.finalproject.repository.AdminReportRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AdminReportService {

    private final AdminReportRepository reportRepository;

    public List<AdminReportDto> getAllReportsWithUserNames() {
        List<Report> reports = reportRepository.findAll();
        return reports.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public AdminReportDto getReportByReportNum(Long reportNum) {
        Report report = reportRepository.findById(reportNum).orElse(null);
        if (report != null) {
            AdminReportDto reportDTO = new AdminReportDto();
            reportDTO.setReportNum(report.getReportNum());
            reportDTO.setReportTime(report.getReportTime());
            reportDTO.setReportDetails(report.getReportDetails());
            reportDTO.setReportPlaced(report.getReportPlaced());
            reportDTO.setReportCategory(report.getReportCategory());
            reportDTO.setReportDegree(report.getReportDegree());
            reportDTO.setUserName(report.getUser().getUsername());
            return reportDTO;
        } else {
            return null;
        }
    }

    private AdminReportDto convertToDTO(Report report) {
        AdminReportDto reportDTO = new AdminReportDto();
        reportDTO.setReportNum(report.getReportNum());
        reportDTO.setReportTime(report.getReportTime());    //컬럼 바꿔서 수정해줘야함
        reportDTO.setReportDetails(report.getReportDetails());
        reportDTO.setReportPlaced(report.getReportPlaced());
        reportDTO.setReportCategory(report.getReportCategory());
        reportDTO.setReportDegree(report.getReportDegree());
        reportDTO.setUserName(report.getUser().getUsername());
        return reportDTO;
    }
}
