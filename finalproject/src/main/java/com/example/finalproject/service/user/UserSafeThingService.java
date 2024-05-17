package com.example.finalproject.service.user;

import com.example.finalproject.domain.SafeThing;
import com.example.finalproject.domain.SafeThingType; // SafeThingType 클래스 import 추가
import com.example.finalproject.dto.UserSafeThingDto;
import com.example.finalproject.repository.UserSafeThingRepository;
import com.example.finalproject.repository.UserSafeThingTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class UserSafeThingService {

    @Autowired
    private UserSafeThingRepository safeThingRepository;

    @Autowired
    private UserSafeThingTypeRepository safeThingTypeRepository;

    public List<UserSafeThingDto> getAllSafeThings() {
        List<SafeThing> safeThings = safeThingRepository.findAll();
        Map<Integer, String> safeThingTypeMap = safeThingTypeRepository.findAll().stream()
                .collect(Collectors.toMap(SafeThingType::getSafeTypeNum, SafeThingType::getSafeName));

        return safeThings.stream()
                .map(safeThing -> {
                    UserSafeThingDto dto = new UserSafeThingDto();
                    dto.setSafeNum(safeThing.getSafeNum());
                    dto.setSafeAddress(safeThing.getSafeAddress());
                    dto.setSafeY(safeThing.getSafeY());
                    dto.setSafeX(safeThing.getSafeX());

                    // 안전 시설 유형 가져오기
                    String safeName = safeThingTypeMap.getOrDefault(safeThing.getSafeThingType().getSafeTypeNum(), "안전시설 이름 없음");
                    dto.setSafeName(safeName);
                    return dto;
                })
                .collect(Collectors.toList());
    }
}
