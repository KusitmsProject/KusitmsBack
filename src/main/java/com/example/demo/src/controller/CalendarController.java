package com.example.demo.src.controller;


import com.example.demo.config.BaseResponse;
import com.example.demo.src.dto.response.*;
import com.example.demo.src.service.CalendarService;
import com.example.demo.src.service.JwtService;
import com.example.demo.src.service.WeatherService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/bring",produces = "application/json;charset=UTF-8")
public class CalendarController {

    private final CalendarService calendarService;
    private final WeatherService weatherService;

    private final JwtService jwtService;

    public CalendarController(CalendarService calendarService, WeatherService weatherService, JwtService jwtService) {
        this.calendarService = calendarService;
        this.weatherService = weatherService;
        this.jwtService = jwtService;
    }

    // youngmin
    @Operation(summary = "오늘 날씨 기반 랜덤 데이터 반환", description = "/bring/calender/random?date={날짜}&place={위치}&userIdx={유저인덱스}")
    @GetMapping("/calender/random")
    public BaseResponse<GetWeatherRandomRes> searchWeather(@RequestParam(value="date")String date, @RequestParam(value="place")String place, @RequestParam(value="userIdx")String userIdx) {
        try{
            GetWeatherRandomRes getWeatherRandomRes = weatherService.randomData(date, place, userIdx);
            return new BaseResponse<>(getWeatherRandomRes);
        } catch (BaseException e) {
            return new BaseResponse<>(e.getStatus());
        }
    }


    // 캘린더뷰 조회


    // 캘린더뷰에 데이터 있는지 없는지 조회하는 API

    // 쿼리에서 반환값이 0 이면 null을 세팅하고
    // 아니면 imageURL을 뱉자
    @GetMapping("/calendar/moment/exists")
    public BaseResponse<List<GetCalendarMomentRes>> getMomentExist(@RequestParam(value="year")String year,@RequestParam(value="month") String month) throws BaseException {

        try{
            Long userIdx=jwtService.getUserIdx();
            List<GetCalendarMomentRes>getMomentExistList=calendarService.getMomentExist(year,month,userIdx);
            return new BaseResponse<>(getMomentExistList);
        }catch(BaseException e){
            return new BaseResponse<>(e.getStatus());
        }
    }
    @GetMapping("/calendar/today/exists")
    public BaseResponse<List<GetCalendarTodayRes>> getTodayExist(@RequestParam(value="year")String year,@RequestParam(value="month") String month) throws BaseException {

        try{
            Long userIdx= jwtService.getUserIdx();
            List<GetCalendarTodayRes>getTodayExistList=calendarService.getTodayExist(year,month,userIdx); //여기가 문제
            return new BaseResponse<>(getTodayExistList);
        }catch(BaseException e){
            return new BaseResponse<>(e.getStatus());
        }
    }

    
    // 그때의 나
    @GetMapping("/calendar/moment")
    public BaseResponse<List<GetCalendarMomentRes>> getCalendarMomentView(@RequestParam(value="year")String year,@RequestParam(value="month") String month) throws BaseException {


       try{
           Long userIdx= jwtService.getUserIdx();
           List<GetCalendarMomentRes> getCalendarMomentRes=calendarService.getCalendarMomentView(year,month,userIdx);



           return new BaseResponse<>(getCalendarMomentRes);
       }catch(BaseException e){
           return new BaseResponse<>(e.getStatus());
       }

    }


    // 오늘의 나
    //

    @GetMapping("/calendar/today")
    public BaseResponse<List<GetCalendarTodayRes>> getCalendarTodayView(@RequestParam(value="year")String year,@RequestParam(value="month") String month) throws BaseException {
        try{

            Long userIdx= jwtService.getUserIdx();
            List<GetCalendarTodayRes> getCalendarTodayRes=calendarService.getCalendarTodayView(year,month,userIdx);

            return new BaseResponse<>(getCalendarTodayRes);
        }catch (BaseException e){
            return new BaseResponse<>(e.getStatus());
        }

    }
    
    // 캘린더뷰 detail

    // 그때의 나 detail
    @GetMapping("/calendar/moment/detail")
    public BaseResponse<GetCalendarMomentDetailRes>getCalendarMomentDetail(@RequestParam(value="year")String year,@RequestParam(value="month")String month,@RequestParam(value="day")String day) throws BaseException {
        try{

            Long userIdx= jwtService.getUserIdx();
            System.out.println(userIdx);
            GetCalendarMomentDetailRes getCalendarMomentDetailRes=calendarService.getCalendarMomentDetail(year,month,day,userIdx);

            return new BaseResponse<>(getCalendarMomentDetailRes);
        }catch (BaseException e){

            return  new BaseResponse<>(e.getStatus());
        }

    }




    // 오늘의 나 detail

    @GetMapping("/calendar/today/detail")
    public BaseResponse <GetCalendarTodayDetailRes>getCalendarTodayDetail(@RequestParam(value="year")String year, @RequestParam(value="month")String month, @RequestParam(value="day")String day) throws BaseException {

        try{

            Long userIdx= jwtService.getUserIdx();
            GetCalendarTodayDetailRes getCalendarTodayDetailRes=calendarService.getCalendarTodayDetail(year,month,day,userIdx);



            return new BaseResponse<>(getCalendarTodayDetailRes);
        }catch(BaseException e){
            return new BaseResponse<>(e.getStatus());
        }
    }
}
