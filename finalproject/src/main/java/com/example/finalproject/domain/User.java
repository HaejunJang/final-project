package com.example.finalproject.domain;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Table(name = "user")
@Entity
@Getter
@Setter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class User implements UserDetails {
    @Id //pk 지정
    @GeneratedValue(strategy = GenerationType.IDENTITY) //DB가 자동으로 1씩 증가
    private Long userNum;

    @Column(name = "userName", nullable = false)
    private String userName;

    @Column(name = "userId",nullable = false)
    private String userId;

    @Column(name = "userPw",nullable = false)
    private String userPw;

    @Column(name = "userBirth")
    private String userBirth;

    @Column(name = "userCall",nullable = false)
    private String userCall;

    @Column(name = "userAddress",nullable = false)
    private String userAddress;

    @Column(name = "userSex",nullable = false)
    private String userSex;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<Setting> settings = new ArrayList<>(); // Service 엔티티와의 일대일 관계 매핑

    @OneToMany(mappedBy = "user")
    private List<Feedback> feedbacks;

    @OneToMany(mappedBy = "user")   //user 엔티티가 관계의 주인
    private List<Report> reports;

    @ElementCollection(fetch = FetchType.EAGER)
    @Builder.Default
    private List<String> roles = new ArrayList<>();

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.roles.stream()
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
    }
    @Override
    public String getUsername() {
        return userName;
    }

    @Override
    public String getPassword() {
        return userPw;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
