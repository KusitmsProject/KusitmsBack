package com.example.demo.src.entity;


import lombok.Builder;
import lombok.Getter;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;


@Entity
@Getter
@Table(name="Music")
public class Music {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long musicIdx;

    @OneToMany(mappedBy = "music")
    private List<Post>posts=new ArrayList<>();

    private String track;

    private String artist;


    //지우지 말것
    private String trackIdx;

    private String videoIdx;


    @Column(name="created",nullable = false, updatable = false, insertable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private Timestamp createdAt;

    @Column(name="updated",nullable = false, updatable = false, insertable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private Timestamp updatedAt;

    @ColumnDefault("'A'")
    private String status;





    @Builder
    public Music(String trackIdx,String track,String artist,String videoIdx){
        this.trackIdx=trackIdx;
        this.track=track;
        this.artist=artist;
        this.videoIdx=videoIdx;

    }



    public Music() {

    }


}
