package com.example.demo.src.dto.response;


import com.example.demo.src.entity.Music;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class GetSpotifyRes {

    private String track;
    private String trackIdx;
    private String artist;


    public static GetSpotifyRes getSpotifyDto(Music music){

        return GetSpotifyRes.builder()
                .track(music.getTrack())
                .trackIdx(music.getTrackIdx())
                .artist(music.getArtist())
                .build();
    }
}
