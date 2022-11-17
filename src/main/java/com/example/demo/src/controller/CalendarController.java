package com.example.demo.src.controller;


import com.example.demo.config.BaseResponse;
import com.example.demo.src.dto.response.GetCalendarMomentRes;
import com.example.demo.src.dto.response.GetCalendarTodayRes;
import com.example.demo.src.service.CalendarService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@RestController
@RequestMapping(value = "/bring",produces = "application/json;charset=UTF-8")
public class CalendarController {

    private final CalendarService calendarService;

    public CalendarController(CalendarService calendarService) {
        this.calendarService = calendarService;
    }


    // 캘린더뷰 조회
    
    // 그때의 나
    @GetMapping("/calendar/moment")
    public BaseResponse<List<GetCalendarMomentRes>> getCalendarMomentView(@RequestParam(value="year")String year,@RequestParam(value="month") String month){


        List<GetCalendarMomentRes> getCalendarMomentRes=calendarService.getCalendarMomentView(year,month);

        return new BaseResponse<>(getCalendarMomentRes);

    }


    // 오늘의 나

    @GetMapping("/calendar/today")
    public BaseResponse<List<GetCalendarTodayRes>> getCalendarTodayView(@RequestParam(value="year")String year,@RequestParam(value="month") String month){
        List<GetCalendarTodayRes> getCalendarTodayRes=calendarService.getCalendarTodayView(year,month);

        return new BaseResponse<>(getCalendarTodayRes);

    }
    
    // 캘린더뷰 detail

    // 그때의 나
    
    // 오늘의 나
}
