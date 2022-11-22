package com.example.demo.src.dto.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class GetUserIdxRes {

    private Long userIdx;
    private String kakao_nickname;
}
