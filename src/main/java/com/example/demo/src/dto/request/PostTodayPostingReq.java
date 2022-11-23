package com.example.demo.src.dto.request;

import lombok.Builder;
import lombok.Data;

@Data

public class PostTodayPostingReq {

    private String track;
    private String artist;
    private String lyrics;
    private String emotion;
    private String imageUrl;
}
