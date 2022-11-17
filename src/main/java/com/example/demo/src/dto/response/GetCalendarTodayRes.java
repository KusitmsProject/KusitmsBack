package com.example.demo.src.dto.response;


import com.example.demo.src.entity.Post;
import lombok.Builder;
import lombok.Data;

import java.time.DayOfWeek;
import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;
import java.util.Locale;

@Data
@Builder
public class GetCalendarTodayRes {

    private String day;
    private String dayOfWeek;
    private String imageURL;

    public static GetCalendarTodayRes getCalendarTodayRes(Post post){
        String dd = post.getCreatedAt().format(DateTimeFormatter.ofPattern("dd"));
        DayOfWeek dayOfWeek =post.getCreatedAt().getDayOfWeek();
        String dayText=dayOfWeek.getDisplayName(TextStyle.SHORT, Locale.US);
        return GetCalendarTodayRes.builder()
                .day(dd)
                .dayOfWeek(dayText)
                .imageURL(post.getImageUrl())
                .build();
    }
}
