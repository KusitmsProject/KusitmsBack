package com.example.demo.src.entity;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

import javax.persistence.*;
import javax.validation.constraints.Email;
import java.sql.Timestamp;
import java.util.Collection;

@Entity
@Table(name="User")
@Getter

public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userIdx;

    private Long kakaoId;

    private String kakaoNickname;
    @Email
    @Column(nullable = false)
    private String kakaoEmail;
    private String profileImgUrl;
    private String gender;
    @Lob
    private String introduction;



    @Column(name="created",nullable = false, updatable = false, insertable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private Timestamp createdAt;


    @Builder
    public User(Long userIdx,Long kakaoId,String profileImgUrl,String kakaoNickname,String kakaoEmail,String gender){
        this.userIdx=userIdx;
        this.kakaoId=kakaoId;
        this.profileImgUrl=profileImgUrl;
        this.kakaoNickname=kakaoNickname;
        this.kakaoEmail=kakaoEmail;
        this.gender=gender;

    }

    @ColumnDefault("'A'")
    private String status;

    public User() {

    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public String getPassword() {
        return null;
    }

    @Override
    public String getUsername() {
        return null;
    }

    @Override
    public boolean isAccountNonExpired() {
        return false;
    }

    @Override
    public boolean isAccountNonLocked() {
        return false;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return false;
    }

    @Override
    public boolean isEnabled() {
        return false;
    }
}