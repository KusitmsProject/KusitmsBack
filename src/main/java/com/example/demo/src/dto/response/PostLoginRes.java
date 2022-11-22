package com.example.demo.src.dto.response;


import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PostLoginRes {

    private String kakao_nickname;
    private Long userIdx;
    private String jwt;
}
