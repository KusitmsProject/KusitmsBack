package com.example.demo.src.dto.response;


import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class GetLastYearRes {

    private String artist;
    private String track;
    private List<String> friendList;
    private String date;

}
