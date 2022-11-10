package com.example.demo.src.dto.request;

import lombok.Data;

@Data
public class PostUserReq {
    private Long kakao_id;
    private String kakao_nickname;
    private String password;
    private String kakao_email;
    private String profile_img_url;
    private String gender;
}