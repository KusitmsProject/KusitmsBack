package com.example.demo.src.repository;

import com.example.demo.src.entity.Post;

import java.time.LocalDateTime;
import java.util.List;

public interface CalendarRepositoryCustom {

    List<Post> findByMonthAndYear(String startDate, String endDate,int options,Long userIdx);

    Post findByMonthYearDay(String startTime,String endTime,int options,Long userIdx);

    int getMomentExist(String startTime,String endTime,Long userIdx);

    int getTodayExist(String startTime,String endTime,Long userIdx);



}
