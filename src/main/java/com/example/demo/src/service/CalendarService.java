package com.example.demo.src.service;


import com.example.demo.config.BaseResponseStatus;
import com.example.demo.src.dto.response.*;
import com.example.demo.src.entity.Post;
import com.example.demo.src.repository.CalendarRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static com.example.demo.src.dto.response.GetCalendarMomentRes.getCalendarMomentNonExist;
import static com.example.demo.src.dto.response.GetCalendarTodayRes.getCalendarTodayNonExist;

@Service
public class CalendarService {

    @Autowired
    private final CalendarRepository calendarRepository;



    public CalendarService(CalendarRepository calendarRepository) {
        this.calendarRepository = calendarRepository;


    }

    public List<GetCalendarMomentRes>getMomentExist(String year,String month){

        List<GetCalendarMomentRes> getCalendarMomentResList=new ArrayList<>();


        
        // 날짜 validation
        int endDay;
        if(month.equals("2")){
            endDay=28;

        }
        else if(month.equals("4")|| month.equals("6")||month.equals("9")||month.equals("11")){
            endDay=30;
        }else{
            endDay=31;
        }

        //월 validation
        if(Integer.parseInt(month)>=1&&Integer.parseInt(month)<=9){
            month="0".concat(month);
        }

        for(int i=1;i<=endDay;i++){
            String stdDay;
            if(i>=1&&i<=9){
                stdDay=year.concat("-").concat(month).concat("-0").concat(Integer.toString(i));

            }
            else{
                stdDay=year.concat("-").concat(month).concat("-").concat(Integer.toString(i));
            }

            LocalDate startTime=LocalDate.parse(stdDay);
            LocalDate endTime=LocalDate.parse(stdDay);

            //createdAt의 초 까지 생각해서 거기서 00:00:00 between 23:59:59로 생각 !
            LocalDateTime localStartTime=startTime.atTime(0,0,0);
            LocalDateTime localEndTime=endTime.atTime(23,59,59);

            String start=localStartTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
            String end=localEndTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));

            int num=calendarRepository.getMomentExist(start,end);

            System.out.println(num);
            if(num==0){
                GetCalendarMomentRes getCalendarMomentRes=getCalendarMomentNonExist(startTime);
                getCalendarMomentResList.add(getCalendarMomentRes);

            }else if(num==1){
                Post post=calendarRepository.findByMonthYearDay(stdDay,stdDay,0);
                GetCalendarMomentRes getCalendarMomentRes=GetCalendarMomentRes.getCalendarMomentRes(post);
                getCalendarMomentResList.add(getCalendarMomentRes);
            }

        }

        return getCalendarMomentResList;

    }

    public List<GetCalendarTodayRes> getTodayExist(String year,String month){
        List<GetCalendarTodayRes> getCalendarTodayResList=new ArrayList<>();


        //날짜 validation
        int endDay;
        if(month.equals("2")){
            endDay=28;

        }
        else if(month.equals("4")|| month.equals("6")||month.equals("9")||month.equals("11")){
            endDay=30;
        }else{
            endDay=31;
        }

        //월 validation
        if(Integer.parseInt(month)>=1&&Integer.parseInt(month)<=9){
            month="0".concat(month);
        }

        for(int i=1;i<=endDay;i++){
            String stdDay;
            if(i>=1&&i<=9){
                stdDay=year.concat("-").concat(month).concat("-0").concat(Integer.toString(i));

            }
            else{
                stdDay=year.concat("-").concat(month).concat("-").concat(Integer.toString(i));
            }

            LocalDate startTime=LocalDate.parse(stdDay);
            LocalDate endTime=LocalDate.parse(stdDay);

            //createdAt의 초 까지 생각해서 거기서 00:00:00 between 23:59:59로 생각 !
            LocalDateTime localStartTime=startTime.atTime(0,0,0);
            LocalDateTime localEndTime=endTime.atTime(23,59,59);

            String start=localStartTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
            String end=localEndTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));

            int num=calendarRepository.getTodayExist(start,end);


            if(num==0){
                GetCalendarTodayRes getCalendarTodayRes=getCalendarTodayNonExist(startTime);
                getCalendarTodayResList.add(getCalendarTodayRes);

            }else if(num==1){
                Post post=calendarRepository.findByMonthYearDay(stdDay,stdDay,1);
                GetCalendarTodayRes getCalendarTodayRes=GetCalendarTodayRes.getCalendarTodayRes(post); // 문제임
                getCalendarTodayResList.add(getCalendarTodayRes);
            }

        }
        return  getCalendarTodayResList;
    }


    public List<GetCalendarMomentRes> getCalendarMomentView(String year,String month){


        //날짜 validation
        int endDay;
        if(month.equals("2")){
            endDay=28;

        }
        else if(month.equals("4")|| month.equals("6")||month.equals("9")||month.equals("11")){
            endDay=30;
        }else{
            endDay=31;
        }

        //월 validation
        if(Integer.parseInt(month)>=1&&Integer.parseInt(month)<=9){
            month="0".concat(month);
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




        //날짜 validation
        int endDay;
        if(month.equals("2")){
            endDay=28;

        }
        else if(month.equals("4")|| month.equals("6")||month.equals("9")||month.equals("11")){
            endDay=30;
        }else{
            endDay=31;
        }

        //월 validation
        if(Integer.parseInt(month)>=1&&Integer.parseInt(month)<=9){
            month="0".concat(month);
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
    public GetCalendarMomentDetailRes getCalendarMomentDetail(String year, String month, String day) throws BaseException {


        String startTime;
        String endTime;

        //월 validation
        if(Integer.parseInt(month)>=1&&Integer.parseInt(month)<=9){
            String zero="0";
            month=zero.concat(month);
        }
        if(Integer.parseInt(day)>=1&&Integer.parseInt(day)<=9){

            startTime=year.concat("-").concat(month).concat("-0").concat(day);
            endTime=year.concat("-").concat(month).concat("-0").concat(day);
        }else{
            startTime=year.concat("-").concat(month).concat("-").concat(day);
            endTime=year.concat("-").concat(month).concat("-").concat(day);
        }



        Post getCalendarMomentDetail=calendarRepository.findByMonthYearDay(startTime,endTime,0);


        //null값일때 처리
        if(getCalendarMomentDetail==null){
            return GetCalendarMomentDetailRes.builder()
                    .track("")
                    .season("")
                    .weather(new ArrayList<>())
                    .date(LocalDate.now())
                    .friendList(new ArrayList<>())
                    .place("")
                    .emotion("")
                    .imageURL("")
                    .record("")
                    .build();
        }

        return GetCalendarMomentDetailRes.builder()
                .track(getCalendarMomentDetail.getMusic().getTrack())
                .artist(getCalendarMomentDetail.getMusic().getArtist())
                .season(getCalendarMomentDetail.getSeason())
                .weather(getCalendarMomentDetail.getWeather())
                .date(getCalendarMomentDetail.getDate())
                .friendList(getCalendarMomentDetail.getFriendList())
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

        //월 validation
        if(Integer.parseInt(month)>=1&&Integer.parseInt(month)<=9){
            month="0".concat(month);
        }
        if(Integer.parseInt(day)>=1&&Integer.parseInt(day)<=9){

            startTime=year.concat("-").concat(month).concat("-0").concat(day);
            endTime=year.concat("-").concat(month).concat("-0").concat(day);
        }else{
            startTime=year.concat("-").concat(month).concat("-").concat(day);
            endTime=year.concat("-").concat(month).concat("-").concat(day);
        }



        Post getCalendarTodayDetail=calendarRepository.findByMonthYearDay(startTime,endTime,1);

        //null값일때 처리
        if(getCalendarTodayDetail==null){
            return GetCalendarTodayDetailRes.builder()
                    .track("")
                    .artist("")
                    .videoID("")
                    .lyrics("")
                    .emotion("")
                    .imageURL("")
                    .build();
        }


        return GetCalendarTodayDetailRes.builder()
                .track(getCalendarTodayDetail.getMusic().getTrack())
                .artist(getCalendarTodayDetail.getMusic().getArtist())
                .videoID(getCalendarTodayDetail.getMusic().getVideoIdx())
                .lyrics(getCalendarTodayDetail.getLyrics())
                .emotion(getCalendarTodayDetail.getEmotion())
                .imageURL(getCalendarTodayDetail.getImageUrl())
                .build();

    }



}
