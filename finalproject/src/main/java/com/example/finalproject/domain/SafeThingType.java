package com.example.finalproject.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

@Table(name = "safe_thing_type")
@Entity
@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class SafeThingType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int safeTypeNum; // 안전시설 구분 번호, PK

    @Column(name = "safeName",nullable = false)
    private String safeName; // 안전시설 명

    @OneToMany(mappedBy = "safeThingType") // 안전시설과의 일대다 관계
    private List<SafeThing> safeThings; // SafeThing 엔티티와 연결

    // Lombok을 사용하여 생성자, Getter, ToString 메소드 자동 생성
}
