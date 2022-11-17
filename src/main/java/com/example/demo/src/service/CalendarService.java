package com.example.demo.src.service;


import com.example.demo.src.dto.response.GetCalendarMomentRes;
import com.example.demo.src.dto.response.GetCalendarTodayRes;
import com.example.demo.src.dto.response.GetSpotifyRes;
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
        String startDate=year.concat("-").concat(month).concat("-01"); //"2022-11-01"
        String endDate=year.concat("-").concat(month).concat("-30"); //"2022-11-30"


        List<Post> getCalendarMomentList=calendarRepository.findByMonthAndYear(startDate,endDate,0);
        
        //DTO 형태로 변환해주기

        return getCalendarMomentList.stream()
                .map(GetCalendarMomentRes::getCalendarMomentRes)
                .collect(Collectors.toList());
        
    }

    public List<GetCalendarTodayRes> getCalendarTodayView(String year,String month){

        String startDate=year.concat("-").concat(month).concat("-01"); //"2022-11-01"
        String endDate=year.concat("-").concat(month).concat("-30"); //"2022-11-30"


        List<Post> getCalendarTodayList=calendarRepository.findByMonthAndYear(startDate,endDate,1);

        //DTO 형태로 변환해주기

        return getCalendarTodayList.stream()
                .map(GetCalendarTodayRes::getCalendarTodayRes)
                .collect(Collectors.toList());

    }
}
