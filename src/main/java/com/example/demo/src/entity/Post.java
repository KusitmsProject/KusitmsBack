package com.example.demo.src.entity;

import com.example.demo.src.utils.StringArrayConverter;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.List;

import static javax.persistence.FetchType.LAZY;

@Getter @Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "POST")
public class Post extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long postIdx; // 게시물 인덱스

    @ManyToOne(fetch = LAZY) // N:1 단방향
    @JoinColumn(name = "musicIdx")
    private Music music; // musicIdx

    @ManyToOne(fetch = LAZY) // N:1 단방향
    @JoinColumn(name = "userIdx")
    private User user; // userIdx

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate date; // 추억 날짜

    private String emotion; // 감정 이모티콘

    private String weather;
//    @Convert(converter = StringArrayConverter.class)
//    private List<String> weather; // 날씨 이모티콘

    private String season; // 계절 이모티콘

    private String lyrics; // 가사

    private String place; // todo : 장소 수정

    private String imageUrl; // 그떄의 사진 url

    private String record; // 그때의 기록

    // 함께 한 사람 리스트
    private String friendList;
//    @Convert(converter = StringArrayConverter.class)
//    private List<String> friendList;

    private Integer options; // 그때의 나인지 오늘의 나인지

}
