package com.example.finalproject.service.user;

import com.example.finalproject.domain.Notice;
import com.example.finalproject.dto.UserNoticeDto;
import com.example.finalproject.repository.UserNoticeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class UserNoticeService {

    @Autowired
    private UserNoticeRepository userNoticeRepository;

    //공지사항 리스트 조회
    public List<UserNoticeDto> getAllNotices() {
        List<Notice> notices = userNoticeRepository.findAll();
        return notices.stream()
                .map(this::mapToDto).collect(Collectors.toList());
    }
    
    //공지사항 세부 조회
    public UserNoticeDto getNoticeByNum(Long postNum) {
        Notice notice =userNoticeRepository.findById(postNum).orElse(null);
        if(notice != null) {
            return mapToDto(notice);
        }
        return null;
    }

    //엔티티를 dto로 변환
    private UserNoticeDto mapToDto(Notice notice) {
        UserNoticeDto dto = new UserNoticeDto();
        dto.setPostNum(notice.getPostNum());
        dto.setAdminName(notice.getAdmin().getAdminName());
        dto.setPostName(notice.getPostName());
        dto.setPostContent(notice.getPostContent());
        dto.setPostDate(notice.getPostDate());
        return dto;
    }
}
