package com.example.finalproject.domain;

import jakarta.persistence.*;
import lombok.*;

@Table(name = "safe_thing")
@Entity
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class SafeThing {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long safeNum; // 안전시설 식별 번호, PK

    @ManyToOne
    @JoinColumn(name = "safeTypeNum") // SafeThingType의 PK를 참조하는 FK
    private SafeThingType safeThingType;

    @Column(name = "safeAddress")
    private String safeAddress; //안전시설 주소

    @Column(name = "safeX", nullable = false)
    private double safeX; // 안전시설 경도값

    @Column(name = "safeY", nullable = false)
    private double safeY; // 안전시설 위도값
}
