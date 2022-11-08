package com.example.demo.src.dto;


import com.example.demo.src.entity.Music;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class GetSpotifyDto {

    private String track;
    private String trackIdx;
    private String artist;


    public static GetSpotifyDto getSpotifyDto(Music music){

        return GetSpotifyDto.builder()
                .track(music.getTrack())
                .trackIdx(music.getTrackIdx())
                .artist(music.getArtist())
                .build();
    }
}
