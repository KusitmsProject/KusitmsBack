package com.example.demo.src.dto.response;

import lombok.Builder;
import lombok.Data;

@Data
public class PostUserRes {

    private Long userIdx;
    private Long kakao_id;
    private String kakao_nickname;
    private String profile_img_url;
    private String gender;
    private String jwt;



    @Builder
    public PostUserRes(Long userIdx, Long kakao_id, String kakao_nickname, String profile_img_url, String gender, String jwt){
        this.userIdx=userIdx;
        this.kakao_id=kakao_id;
        this.kakao_nickname=kakao_nickname;
        this.profile_img_url=profile_img_url;
        this.gender=gender;
        this.jwt=jwt;

    }



}
