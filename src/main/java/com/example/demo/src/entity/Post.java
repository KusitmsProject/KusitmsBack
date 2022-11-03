package com.example.demo.src.entity;

import lombok.Builder;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.sql.Timestamp;

public class Post extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long postIdx; // 게시물 인덱스

    //todo : userIdx 외래키 등록

    @Column(length = 45)
    private String title; // 글 제목

    private String weather; // 날씨

    // 글 게시 시간
    private Timestamp time;

    // 지역 시 구 동
    // 활동 예정 지역코드 2개 (온라인값이 1이면 둘 다 디폴트 값)
    @Builder.Default
    private String regionIdx1 = "0000000000"; //지역 1
    @Builder.Default
    private String regionIdx2 = "0000000000"; //지역 2
    @Builder.Default
    private String regionIdx3 = "0000000000"; //지역 3

    // 기억하고 싶은 일(텍스트)
    private String text;

    // 함께 한 사람

    // 주파수 idx
    private Long frequencyIdx;

    // 유튜브 아이디
    private String videoID;

}
