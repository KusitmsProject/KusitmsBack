package com.example.demo.src.dto.response;

import lombok.Builder;
import lombok.Data;

@Data

public class GetYouTubeRes {

    private String videoId;
    private String videoTitle;
    private String videoURL;

    @Builder
    public GetYouTubeRes(String videoId,String videoTitle,String videoURL){
        this.videoId=videoId;
        this.videoTitle=videoTitle;
        this.videoURL=videoURL;
    }



}
