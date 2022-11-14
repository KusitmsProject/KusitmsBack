package com.example.demo.src.dto.response;

import lombok.*;

@Data
public class GetWeatherRes {

    private String date; // 날짜
    private String weather; // 비, 눈, 맑음, 흐림
    private Integer temp; // 0 : 그외 1 : 더움, 2 : 추움

    @Builder
    public GetWeatherRes(String date, String weather, Integer temp) {
        this.date = date;
        this.weather = weather;
        this.temp = temp;
    }
}
