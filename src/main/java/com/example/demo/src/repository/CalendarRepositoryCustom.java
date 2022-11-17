package com.example.demo.src.repository;

import com.example.demo.src.entity.Post;

import java.util.List;

public interface CalendarRepositoryCustom {

    List<Post> findByMonthAndYear(String startDate, String endDate,int options);
}