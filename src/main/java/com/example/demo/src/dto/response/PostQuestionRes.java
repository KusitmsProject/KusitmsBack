package com.example.demo.src.dto.response;


import lombok.Data;

@Data

public class PostQuestionRes {

    private Long questionIdx;

    public PostQuestionRes(Long questionIdx) {
        this.questionIdx=questionIdx;
    }
}
