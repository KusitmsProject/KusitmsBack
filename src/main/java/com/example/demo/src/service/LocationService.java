package com.example.demo.src.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class LocationService {

    private final KakaoAPI kakaoAPI;

    public String getPlace() {
        return kakaoAPI.getPlace();
    }


}