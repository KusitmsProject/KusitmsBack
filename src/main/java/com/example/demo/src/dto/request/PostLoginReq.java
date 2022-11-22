package com.example.demo.src.dto.request;


import lombok.Data;

@Data
public class PostLoginReq {

    private String email;
    private String password;
    private String kakao_nickname;
}
