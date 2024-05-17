package com.example.finalproject.repository;


import com.example.finalproject.domain.SafeThing;
import com.example.finalproject.domain.SafeThingType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AdminSafeThingTypeRepository extends JpaRepository<SafeThingType, Long> {
    SafeThingType findBySafeName(String safeTypeName);

    SafeThingType findBySafeTypeNum(int safeTypeNum);

}
