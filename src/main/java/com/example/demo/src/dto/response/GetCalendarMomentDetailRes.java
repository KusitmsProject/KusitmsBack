package com.example.demo.src.dto.response;

import com.example.demo.src.entity.Post;
import lombok.Builder;
import lombok.Data;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;
import java.util.List;
import java.util.Locale;

@Data
@Builder
public class GetCalendarMomentDetailRes {

    private String track;
    private String artist;
    private String season;
    private List<String> weather;
    private LocalDate date;
    private List<String> friendList;
    private String place;
    private String placeNickname;
    private String emotion;
    private String imageURL;
    private String record;

    public static GetCalendarMomentDetailRes getCalendarMomentDetailRes(Post post){

        return GetCalendarMomentDetailRes.builder()
                .track(post.getMusic().getTrack())
                .artist(post.getMusic().getArtist())
                .season(post.getSeason())
                .weather(post.getWeather())
                .date(post.getDate())
                .place(post.getPlace())
                .placeNickname(post.getPlaceNickname())
                .emotion(post.getEmotion())
                .imageURL(post.getImageUrl())
                .record(post.getRecord())
                .build();
    }
}
