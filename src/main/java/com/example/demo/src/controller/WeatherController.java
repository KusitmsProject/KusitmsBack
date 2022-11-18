package com.example.demo.src.controller;

import com.example.demo.config.BaseResponse;
import com.example.demo.src.dto.response.BaseException;
import com.example.demo.src.dto.response.GetWeatherRes;
import com.example.demo.src.dto.response.PostPostingRes;
import com.example.demo.src.service.WeatherService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/bring",produces = "application/json;charset=UTF-8")
public class WeatherController {
    private final WeatherService weatherService;

    public WeatherController(WeatherService weatherService) {
        this.weatherService = weatherService;
    }

    // 날씨 정보 검색
    @Operation(summary = "날씨 정보 받아오기", description = "/bring/weather?date={날짜}&place={장소}")
    @GetMapping("/weather")
    public BaseResponse<List<GetWeatherRes>> searchWeather(@RequestParam(value="date")String date, @RequestParam(value="place")String place) {
        try{
            List<GetWeatherRes> getWeatherResList = weatherService.weatherSearch(date, place);
            return new BaseResponse<>(getWeatherResList);
        } catch (BaseException e) {
            return new BaseResponse<>(e.getStatus());
        }
    }
}
