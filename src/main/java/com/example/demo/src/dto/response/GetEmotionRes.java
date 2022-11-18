package com.example.demo.src.dto.response;

import com.example.demo.src.entity.Post;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.List;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class GetEmotionRes {
    @ApiModelProperty(notes = "추억 날짜", example = "2022-11-09")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate date; // 추억 날짜

    private Long postIdx;

    @ApiModelProperty(notes = "그떄의 나 : 0, 오늘의 나 : 1", example = "윤아 지원")
    private Integer options; // 그때의 나인지 오늘의 나인지
}
