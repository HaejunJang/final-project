package com.example.finalproject.domain;

import jakarta.persistence.*;
import lombok.*;

@Table(name = "feedback")
@Entity
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Feedback {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long feedNum; //후기 식별번호, PK

    @ManyToOne
    @JoinColumn(name = "userNum")
    private User user; // User 엔티티와의 관계를 나타내는 필드, FK

    @Column(name = "feedGrade",nullable = false)
    private int feedGrade; // 회원 별점

    @Column(name = "feedContent",nullable = false)
    private String feedContent;    //회원 피드백
    
}
