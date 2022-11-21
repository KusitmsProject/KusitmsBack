package com.example.demo.src.dto.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class GetWeatherRandomRes {

    private String date; // 날짜
    private String weather; // 비, 눈, 맑음, 흐림
    private Integer temp; // 0 : 그외 1 : 더움, 2 : 추움
    private String artist; // 아티스트
    private String track; // 노래 제목

}
