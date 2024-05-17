package com.example.finalproject.repository;

import com.example.finalproject.domain.SafeThing;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserSafeThingRepository extends JpaRepository<SafeThing, Long> {
}
