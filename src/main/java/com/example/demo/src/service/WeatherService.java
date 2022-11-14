package com.example.demo.src.service;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import com.example.demo.src.dto.response.BaseException;
import com.example.demo.src.dto.response.GetWeatherRes;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
@PropertySource("classpath:weather.properties")
public class WeatherService {

    @Value("${api_key}")
    private static String apikey;

    // tag값의 정보를 가져오는 함수
    public static String getTagValue(String tag, Element eElement) {

        //결과를 저장할 result 변수 선언
        String result = "";

        NodeList nlList = eElement.getElementsByTagName(tag).item(0).getChildNodes();

        if(nlList.item(0) == null)
            return "";

        result = nlList.item(0).getTextContent();

        return result;
    }

    public static List<GetWeatherRes> weatherSearch(String date, String place) throws BaseException {
        List<GetWeatherRes> result = new ArrayList<>();

        try{
            String placeNum = "108";

            // url (현재 기능상 시작, 종료날짜 통일)
            String url = "http://apis.data.go.kr/1360000/AsosDalyInfoService/getWthrDataList?serviceKey=" + apikey
                    + "&numOfRows=10&pageNo=1&dataCd=ASOS&dateCd=DAY&startDt=" + date + "&endDt=" + date + "&stnIds=" + placeNum;

            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(url);

            // 제일 첫번째 태그
            doc.getDocumentElement().normalize();

            // 파싱할 tag
            NodeList nList = doc.getElementsByTagName("item");

            for(int temp = 0; temp < nList.getLength(); temp++){
                Node nNode = nList.item(temp);

                Element eElement = (Element) nNode;

//                System.out.println("위치 : " + getTagValue("stnNm", eElement));
//                System.out.println("위치번호 : " + getTagValue("stnId", eElement));
                System.out.println("날짜 : " + getTagValue("tm", eElement));
//                System.out.println("평균 기온 : " + getTagValue("avgTa", eElement));
//                System.out.println("최저 기온 : " + getTagValue("minTa", eElement));
//                System.out.println("최고 기온 : " + getTagValue("maxTa", eElement));
//                System.out.println("강수 지속시간 : " + getTagValue("sumRnDur", eElement));
//                System.out.println("일 강수량 mm : " + getTagValue("sumRn", eElement));
//                System.out.println("평균 풍속 m/s : " + getTagValue("avgWs", eElement));
//                System.out.println("평균 상대습도 % : " + getTagValue("avgRhm", eElement));
//                System.out.println("평균 전운량 (10분위) : " + getTagValue("avgTca", eElement));
//                System.out.println("일 최심신적설(눈 cm) : " + getTagValue("ddMefs", eElement));
                System.out.println("일기현상 : " + getTagValue("iscs", eElement));

                // 날씨 구분
                String cloud = getTagValue("avgTca", eElement);
                String weather = getTagValue("iscs", eElement);
                if(weather.indexOf("눈") != -1)
                    System.out.println("눈옴");
                else if(weather.indexOf("비") != -1 || weather.indexOf("소나기") != -1)
                    System.out.println("비옴");
                    // 전운량 7이상이면 흐림 처리 (기상청 기준 0~5 맑음, 6~8 구름많음, 9~10 흐림)
                else if(Float.parseFloat(cloud) >= 7)
                    System.out.println("흐림");
                else System.out.println("맑음");

                // 더움 추분 구분
                String maxTemp = getTagValue("maxTa", eElement);
                String avgTemp = getTagValue("avgTa", eElement);
                if(Float.parseFloat(maxTemp) >= 30)
                    System.out.println("더움");
                else if(Float.parseFloat(avgTemp) < 0)
                    System.out.println("추움");

            }

        } catch (Exception e){
            e.printStackTrace();
        }



        return result;
    }

}