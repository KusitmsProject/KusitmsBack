package com.example.demo.src.dto;

import lombok.Data;

@Data
public class PostUserDto {
    private Long kakao_id;
    private String kakao_nickname;
    private String password;
    private String kakao_email;
    private String profile_img_url;
    private String gender;
}