package com.example.demo.src.dto.request;


import lombok.Data;

import java.util.Optional;

@Data
public class PostQuestionReq {

    private String questionText;
    private String imageUrl;
    private String videoIdx;
}
