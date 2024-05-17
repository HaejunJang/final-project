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

@Builder
@Table(name = "admin")
@Entity
@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Admin implements UserDetails {
    @Id //pk 지정
    @GeneratedValue(strategy = GenerationType.IDENTITY) //DB가 자동으로 1씩 증가
    private Long adminNum;

    @Column(name = "adminName", nullable = false) // 명시적으로 컬럼 이름 지정
    private String adminName;

    @Column(name = "adminId", nullable = false) // 명시적으로 컬럼 이름 지정
    private String adminId;

    @Column(name = "adminPw", nullable = false) // 명시적으로 컬럼 이름 지정
    private String adminPw;

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
    public String getPassword() {
        return adminPw;
    }

    @Override
    public String getUsername() {
        return adminId;
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

    @OneToMany(mappedBy = "admin") // 관리자와의 일대다 관계
    private List<Notice> notices; // Notice 엔티티와 연결

}
