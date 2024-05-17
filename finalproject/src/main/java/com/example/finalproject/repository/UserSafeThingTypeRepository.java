package com.example.finalproject.repository;

import com.example.finalproject.domain.SafeThingType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserSafeThingTypeRepository extends JpaRepository<SafeThingType, Integer> {
}
