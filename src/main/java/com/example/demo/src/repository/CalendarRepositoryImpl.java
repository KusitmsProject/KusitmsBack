package com.example.demo.src.repository;

import com.example.demo.src.entity.Post;
import com.example.demo.src.entity.QPost;
import com.querydsl.core.types.ConstantImpl;
import com.querydsl.core.types.dsl.DateTemplate;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;


@RequiredArgsConstructor
public class CalendarRepositoryImpl implements  CalendarRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    public List<Post> findByMonthAndYear(String startDate, String endDate,int options){
        QPost post=QPost.post;


        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        LocalDate formattedStart= LocalDate.parse(startDate);
        LocalDate formattedEnd=LocalDate.parse(endDate);


        return  queryFactory.select(post)
                .from(post)
                .where(

                       post.options.eq(options), post.createdPost.between(formattedStart,formattedEnd)
                )
                .fetch();



    }

}
