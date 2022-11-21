package com.example.demo.src.dto.response;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class GetSearchTrackRes {
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate date; // 추억 날짜

    private Long postIdx;

    private Long musicIdx;

    private String videoId; // 유튜브 비디오 id

    private String artist; // 가수

    private String track; // 곡 제목
    
    private String lyrics;// 가사 4줄

    private String emotion; // 감정

    private Integer options; // 그때의 나인지 오늘의 나인지
}
