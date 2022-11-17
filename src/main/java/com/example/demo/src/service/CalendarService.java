package com.example.demo.src.service;


import com.example.demo.src.dto.response.*;
import com.example.demo.src.entity.Post;
import com.example.demo.src.repository.CalendarRepository;
import com.example.demo.src.repository.CalendarRepositoryCustom;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CalendarService {

    @Autowired
    private final CalendarRepository calendarRepository;



    public CalendarService(CalendarRepository calendarRepository, CalendarRepositoryCustom calendarRepositoryCustom) {
        this.calendarRepository = calendarRepository;

    }

    public List<GetCalendarMomentRes> getCalendarMomentView(String year,String month){

        int endDay;
        if(month.equals("2")){
            endDay=28;

        }
        else if(month.equals("4")|| month.equals("6")||month.equals("9")||month.equals("11")){
            endDay=30;
        }else{
            endDay=31;
        }

        String startDate=year.concat("-").concat(month).concat("-01");
        String endDate=year.concat("-").concat(month).concat("-").concat(Integer.toString(endDay));


        List<Post> getCalendarMomentList=calendarRepository.findByMonthAndYear(startDate,endDate,0);
        
        //DTO 형태로 변환해주기

        return getCalendarMomentList.stream()
                .map(GetCalendarMomentRes::getCalendarMomentRes)
                .collect(Collectors.toList());
        
    }

    public List<GetCalendarTodayRes> getCalendarTodayView(String year,String month){


        int endDay;
        if(month.equals("2")){
            endDay=28;

        }
        else if(month.equals("4")|| month.equals("6")||month.equals("9")||month.equals("11")){
            endDay=30;
        }else{
            endDay=31;
        }

        String startDate=year.concat("-").concat(month).concat("-01");
        String endDate=year.concat("-").concat(month).concat("-").concat(Integer.toString(endDay));


        List<Post> getCalendarTodayList=calendarRepository.findByMonthAndYear(startDate,endDate,1);

        //DTO 형태로 변환해주기

        return getCalendarTodayList.stream()
                .map(GetCalendarTodayRes::getCalendarTodayRes)
                .collect(Collectors.toList());

    }

    //그때의 나 디테일
    public GetCalendarMomentDetailRes getCalendarMomentDetail(String year,String month,String day){

        String startTime;
        String endTime;
        if(Integer.parseInt(day)>=1&&Integer.parseInt(day)<=9){

            startTime=year.concat("-").concat(month).concat("-0").concat(day);
            endTime=year.concat("-").concat(month).concat("-0").concat(day);
        }else{
            startTime=year.concat("-").concat(month).concat("-").concat(day);
            endTime=year.concat("-").concat(month).concat("-").concat(day);
        }

        Post getCalendarMomentDetail=calendarRepository.findByMonthYearDay(startTime,endTime,0);

        return GetCalendarMomentDetailRes.builder()
                .track(getCalendarMomentDetail.getMusic().getTrack())
                .season(getCalendarMomentDetail.getSeason())
                .weather(getCalendarMomentDetail.getWeather())
                .date(getCalendarMomentDetail.getDate())
                .place(getCalendarMomentDetail.getPlace())
                .emotion(getCalendarMomentDetail.getEmotion())
                .imageURL(getCalendarMomentDetail.getImageUrl())
                .record(getCalendarMomentDetail.getRecord())
                .build();
    }
    
    //오늘의 나 디테일

    public GetCalendarTodayDetailRes getCalendarTodayDetail(String year,String month,String day){
        String startTime;
        String endTime;
        if(Integer.parseInt(day)>=1&&Integer.parseInt(day)<=9){

            startTime=year.concat("-").concat(month).concat("-0").concat(day);
            endTime=year.concat("-").concat(month).concat("-0").concat(day);
        }else{
            startTime=year.concat("-").concat(month).concat("-").concat(day);
            endTime=year.concat("-").concat(month).concat("-").concat(day);
        }
        Post getCalendarTodayDetail=calendarRepository.findByMonthYearDay(startTime,endTime,1);

        return GetCalendarTodayDetailRes.builder()
                .track(getCalendarTodayDetail.getMusic().getTrack())
                .lyrics(getCalendarTodayDetail.getLyrics())
                .emotion(getCalendarTodayDetail.getLyrics())
                .imageURL(getCalendarTodayDetail.getImageUrl())
                .build();

    }



}
