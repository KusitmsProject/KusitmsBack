package com.example.demo.src.dto.response;

import lombok.*;

@Data
@Getter
public class GetWeatherRes {

    private String weather; // 비, 눈, 맑음, 흐림
    private Integer temp; // 0 : 그외 1 : 더움, 2 : 추움

    @Builder
    public GetWeatherRes(String weather, Integer temp) {
        this.weather = weather;
        this.temp = temp;
    }
}
