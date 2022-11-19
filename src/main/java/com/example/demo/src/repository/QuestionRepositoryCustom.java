package com.example.demo.src.repository;

import com.example.demo.src.entity.Question;

public interface QuestionRepositoryCustom {


    Question getQuestionByDate(String year, String month, String day);

}
