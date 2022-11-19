package com.example.demo.src.dto.response;



import com.example.demo.src.entity.Question;
import lombok.Builder;
import lombok.Data;

@Data
@Builder

public class GetQuestionRes {

    private Long questionIdx;
    private String questionText;
    private String imageUrl;
    private String videoIdx;

    public static GetQuestionRes getQuestionRes(Question question){

        return GetQuestionRes.builder()
                .questionIdx(question.getQuestionIdx())
                .questionText(question.getQuestionText())
                .imageUrl(question.getImageUrl())
                .videoIdx(question.getVideoIdx())
                .build();
    }
}
