package com.example.demo.src.entity;


import com.google.api.client.repackaged.com.google.common.base.Optional;
import lombok.Builder;
import lombok.Getter;

import javax.persistence.*;


@Entity
@Getter
@Table(name="Question")
public class Question extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long questionIdx;

    private String questionText;

    private String imageUrl;

    private String videoIdx;



    private String status;

    @Builder
    public Question(Long questionIdx,String questionText,String imageUrl,String videoIdx){
        this.questionIdx=questionIdx;
        this.questionText=questionText;
        this.imageUrl=imageUrl;
        this.videoIdx=videoIdx;
    }


    public Question() {

    }
}
