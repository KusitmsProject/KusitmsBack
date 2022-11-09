package com.example.demo.src.entity;


import lombok.Builder;
import lombok.Getter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;


@Entity
@Getter

public class Music {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long musicIdx;

    private String trackIdx;

    private String track;

    private String artist;




    @Builder
    public Music(String trackIdx,String track,String artist){

        this.trackIdx=trackIdx;
        this.track=track;
        this.artist=artist;

    }



    public Music() {

    }
}
