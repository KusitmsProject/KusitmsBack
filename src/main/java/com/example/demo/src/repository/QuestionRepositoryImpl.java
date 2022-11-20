package com.example.demo.src.repository;


import com.example.demo.src.entity.QQuestion;
import com.example.demo.src.entity.Question;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@RequiredArgsConstructor
public class QuestionRepositoryImpl implements  QuestionRepositoryCustom{


    private final JPAQueryFactory queryFactory;

    @Override
    public Question getQuestionByDate(String year,String month,String day){

        QQuestion question=QQuestion.question;

        // 월 validation
        if(Integer.parseInt(month)>=1&&Integer.parseInt(month)<=9){
            month="0".concat(month);
        }

        // day validation

        if(Integer.parseInt(day)>=1&&Integer.parseInt(day)<=9){
            day="0".concat(day);
        }

        String stdDay=year.concat("-").concat(month).concat("-").concat(day);


        LocalDate startTime=LocalDate.parse(stdDay);
        LocalDate endTime=LocalDate.parse(stdDay);
        LocalDateTime localStartTime=startTime.atTime(0,0,0);
        LocalDateTime localEndTime=endTime.atTime(23,59,59);

        return  queryFactory.select(question)
                .from(question)
                .where(

                        question.createdAt.between(localStartTime,localEndTime)
                )
                .fetchOne();

    }
}
