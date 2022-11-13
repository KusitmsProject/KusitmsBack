package com.example.demo.src.dto.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.List;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class PostPostingReq {
    private Long userIdx;

    @ApiModelProperty(notes = "추억 날짜", example = "2022-11-09")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate date; // 추억 날짜

    @ApiModelProperty(notes = "감정 이모지 유니코드", example = "0x1F425")
    private String emotion; // 감정 이모티콘

    // todo
    @ApiModelProperty(notes = "날씨 이모티콘 코드 리스트", example = "SUNNY")
    private List<String> weather; // 날씨 이모티콘

    @ApiModelProperty(notes = "계절 이모지 유니코드", example = "0x1F425")
    private String season; // 계절 이모티콘

    @ApiModelProperty(notes = "선택한 한 줄 가사", example = "사랑인걸 사랑인걸")
    private String lyrics; // 가사

    @ApiModelProperty(notes = "장소 별칭", example = "우리 집")
    private String placeNickname; // 장소 별칭

    @ApiModelProperty(notes = "장소", example = "서울시 동작구 상도로 369")
    private String place; // 장소

    @ApiModelProperty(notes = "사진 url, image api 리턴 값", example = "https://")
    private String imageUrl; // 그떄의 사진 url

    @ApiModelProperty(notes = "그때의 기록", example = "아 이때 젊었었는데")
    private String record; // 그때의 기록

    @ApiModelProperty(notes = "노래 이름", example = "Hype Boy")
    private String track; // 노래 이름

    @ApiModelProperty(notes = "가수 이름", example = "Newjeans")
    private String artist; // 가수 이름

    // todo
    @ApiModelProperty(notes = "친구 리스트", example = "윤아 지원")
    private List<String> friendList; // 함께 한 사람 리스트

    @ApiModelProperty(notes = "그떄의 나 : 0, 오늘의 나 : 1", example = "윤아 지원")
    private Integer options; // 그때의 나인지 오늘의 나인지
}
