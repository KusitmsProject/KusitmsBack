package com.example.demo.src.dto.response;


import com.example.demo.src.entity.Post;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class GetCalendarTodayDetailRes {


    private String track;
    private String lyrics;
    private String emotion;
    private String imageURL;

    public static GetCalendarTodayDetailRes getCalendarTodayDetailRes(Post post){

        return GetCalendarTodayDetailRes.builder()
                .track(post.getMusic().getTrack())
                .lyrics(post.getLyrics())
                .emotion(post.getEmotion())
                .imageURL(post.getImageUrl())
                .build();
    }
}
