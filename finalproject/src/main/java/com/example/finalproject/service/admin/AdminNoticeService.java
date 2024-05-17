package com.example.finalproject.service.admin;

import com.example.finalproject.domain.Admin;
import com.example.finalproject.domain.Notice;
import com.example.finalproject.dto.AdminNoticeRequestDto;
import com.example.finalproject.dto.AdminNoticeResponseDto;
import com.example.finalproject.repository.AdminNoticeRepository;
import com.example.finalproject.repository.AdminRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AdminNoticeService {

    @Autowired
    private AdminNoticeRepository adminNoticeRepository;

    @Autowired
    private AdminRepository adminRepository;

    public void saveNotice(AdminNoticeRequestDto noticeRequestDto) {
        //현재 시간 기록
        LocalDateTime now = LocalDateTime.now();

        //시간 형식 지정
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        String formattedDateTime = now.format(formatter);

        //관리자 가져오기
        Admin admin = adminRepository.findById(1L)
                .orElseThrow(()->new IllegalArgumentException("해당하는 관리자가 없습니다."));

        //공지사항 저장
        Notice notice = Notice.builder()
                .postName(noticeRequestDto.getPostName())
                .postContent(noticeRequestDto.getPostContent())
                .postDate(formattedDateTime)
                .admin(admin)
                .build();
        adminNoticeRepository.save(notice);
    }

    public List<AdminNoticeResponseDto> getAllData() {
        List<Notice> notices = adminNoticeRepository.findAll();

        return notices.stream()
                .map(this::mapToAdminNoticeResponseDto)
                .collect(Collectors.toList());
    }
    private AdminNoticeResponseDto mapToAdminNoticeResponseDto(Notice notice) {
        String adminName = adminRepository.findById(notice.getAdmin().getAdminNum())
                .map(Admin::getAdminName)
                .orElse("Unknown Admin");

        return AdminNoticeResponseDto.builder()
                .postNum(notice.getPostNum())
                .postName(notice.getPostName())
                .postContent(notice.getPostContent())
                .postDate(notice.getPostDate())
                .adminName(adminName)
                .build();
    }

    public boolean deletePost(Long postNum) {
        Optional<Notice> optionalNotice = adminNoticeRepository.findById(postNum);
        if (optionalNotice.isPresent()) {
            adminNoticeRepository.delete(optionalNotice.get());
            return true;    //삭제 성공
        } else {
            return false;   //삭제 실패 (데이터 존재하지 않음)
        }
    }
}
