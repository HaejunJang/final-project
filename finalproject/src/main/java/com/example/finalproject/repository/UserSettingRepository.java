package com.example.finalproject.repository;

import com.example.finalproject.domain.Prediction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface UserSettingRepository extends JpaRepository<Prediction, Long> {

    Prediction findByAddressAndTime(String address,String time);
}