package com.example.demo.src.repository;

import com.example.demo.src.entity.Post;

import com.example.demo.src.entity.QPost;
import com.querydsl.core.types.ConstantImpl;
import com.querydsl.core.types.ExpressionUtils;
import com.querydsl.core.types.dsl.DateTemplate;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.thymeleaf.standard.expression.ExpressionSequenceUtils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import static com.example.demo.src.entity.QPost.post;

//import static com.example.demo.src.entity.QPost.post;


@RequiredArgsConstructor
public class CalendarRepositoryImpl implements  CalendarRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Autowired
    private JdbcTemplate template;


    // 그때의 나 캘린더 뷰 데이터 있는지 없는지 조회
    @Override
    public int getMomentExist(String startTime,String endTime){
        String sql="SELECT exists(select * from POST where (createdAt between ? and ?) and POST.options=0) ";
        Object[] params={startTime,endTime};

        return this.template.queryForObject(sql,params,int.class);

    }

    //오늘의 나 캘린더 뷰 데이터 있는지 없는지 조회

    @Override
    public int getTodayExist(String startTime,String endTime){
        String sql="SELECT exists(select * from POST where (createdAt between ? and ?) and POST.options=1 )";
        Object[] params={startTime,endTime};

        return this.template.queryForObject(sql,params,int.class);

    }



    // 서로 다른 두 날짜 사이를 비교할 때 
    @Override
    public List<Post> findByMonthAndYear(String startDate, String endDate,int options){
        QPost post= QPost.post;


        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        LocalDate formattedStart= LocalDate.parse(startDate);
        LocalDate formattedEnd=LocalDate.parse(endDate);
        LocalDateTime localStartTime=formattedStart.atTime(0,0,0);
        LocalDateTime localEndTime=formattedEnd.atTime(23,59,59);


        return  queryFactory.select(post)
                .from(post)
                .where(

                        post.options.eq(options), post.createdAt.between(localStartTime,localEndTime)
                )
                .fetch();



    }
    
    // 하나의 날짜 조회, 그 안에서 시간이 00:00:00 부터 23:59:59까지

    @Override

    public Post findByMonthYearDay(String startTime,String endTime,int options){

        LocalDate startTime1=LocalDate.parse(startTime);
        LocalDate endTime1=LocalDate.parse(endTime);

        //createdAt의 초 까지 생각해서 거기서 00:00:00 between 23:59:59로 생각 !
        LocalDateTime localStartTime=startTime1.atTime(0,0,0);
        LocalDateTime localEndTime=endTime1.atTime(23,59,59);


        QPost qPost= post;



        return  queryFactory.select(post)
                .from(post)
                .where(

                        post.options.eq(options), post.createdAt.between(localStartTime,localEndTime)
                )
                .fetchOne();

    }




}
