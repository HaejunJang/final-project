package com.example.finalproject.service.admin;

import com.example.finalproject.domain.User;
import com.example.finalproject.dto.AdminUserDto;
import com.example.finalproject.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AdminUserService {
//관리자 회원 조회 서비스
    @Autowired
    private UserRepository userRepository;

    public List<AdminUserDto> getAllUsers() {
        List<User> users = userRepository.findAll();//db의 모든 user 정보 list로 반환
        return users.stream()//리스트 정보를 스트림으로 변환
                .map(this::convertToDto)//각정보를 dto로 바꿔
                .collect(Collectors.toList());//바꾼 dto 객체가 포함된 리스트로 바꿔
    }

    public AdminUserDto getUserByUserNum(Long userNum) {
        User user = userRepository.findByUserNum(userNum);
        if(user != null) {
            return convertToDto(user);
        }else {
            return null;
        }
    }


    private AdminUserDto convertToDto(User user) {
        AdminUserDto adminUserDto = new AdminUserDto();
        adminUserDto.setUserNum(user.getUserNum());
        adminUserDto.setUserName(user.getUsername());
        adminUserDto.setUserId(user.getUserId());
        adminUserDto.setUserAddress(user.getUserAddress());
        adminUserDto.setUserBirth(user.getUserBirth());
        adminUserDto.setUserSex(user.getUserSex());
        adminUserDto.setUserCall(user.getUserCall());
        return adminUserDto;
    }
}
