package com.example.demo.src.service;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import com.example.demo.src.dto.response.BaseException;
import com.example.demo.src.dto.response.GetWeatherRandomRes;
import com.example.demo.src.dto.response.GetWeatherRes;
import com.example.demo.src.entity.Post;
import com.example.demo.src.entity.User;
import com.example.demo.src.repository.MusicRepository;
import com.example.demo.src.repository.PostRepository;
import com.example.demo.src.repository.UserRepository;
import io.swagger.models.auth.In;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
@Transactional
@PropertySource("classpath:weather.properties")
public class WeatherService {
    private static String apikey; // 기상청 api key
    private final KakaomapService kakaomapService;
    private final PostRepository postRepository;
    private final UserRepository userRepository;

    public WeatherService(KakaomapService kakaomapService, PostRepository postRepository, UserRepository userRepository, MusicRepository musicRepository) {
        this.kakaomapService = kakaomapService;
        this.postRepository = postRepository;
        this.userRepository = userRepository;
    }

    // static에 @Value 안되므로 주입
    @Value("${key}")
    public void setApikey(String key) {
        apikey = key;
    }

    // tag값의 정보를 가져오는 함수
    public String getTagValue(String tag, Element eElement) {

        //결과를 저장할 result 변수 선언
        String result = "";

        NodeList nlList = eElement.getElementsByTagName(tag).item(0).getChildNodes();

        if(nlList.item(0) == null)
            return "";

        result = nlList.item(0).getTextContent();

        return result;
    }

    // 해당 위치와 가장 가까운 관측소 찾아주는 함수
    public String getNearObservatory(String place){
        List<String> coordinates = kakaomapService.getCoordinates(place); // 현재 위치 좌표 얻기

        // 좌표 없는 경우 서울 기준
        if(coordinates.size() == 0)
            return "108";
        Double x = Double.parseDouble(coordinates.get(0));
        Double y = Double.parseDouble(coordinates.get(1));

        String Observatories[] = {"속초","철원","동두천","문산","대관령","춘천","백령","강릉","동해","서울",
                "인천","원주","울릉도","수원","영월","충주","서산","울진","청주","대전","추풍령","안동","상주",
                "포항","군산","대구","전주","울산","마산","광주","부산","통영","목포","여수","흑산도","완도",
                "진도","제주","고산","서귀포","진주","강화","양평","이천","인제","홍천","태백","제천","보은",
                "천안","보령","부여","금산","부안","임실","정읍","남원","장수","장흥","해남","고흥","봉화",
                "영주","문경","영덕","의성","구미","영천","거창","합천","밀양","산청","거제","남해"};

        Integer ObserCode[] = {90,95,98,99,100,101,102,105,106,108,112,114,115,119,121,127,129,
                130,131,133,135,136,137,138,140,143,146,152,155,156,159,162,165,168,169,170,
                175,184,185,189,192,201,202,203,211,212,216,221,226,232,235,236,238,243,244,
                245,247,248,260,261,262,271,272,273,277,278,279,281,284,285,288,289,294,295};


        Double ObserY[] = {38.2481,38.1451,37.8991,37.8844,37.6841,37.8999,37.9667,37.7486,37.5043,
                37.57141,37.4747,37.334,37.48,37.2695,37.1784,36.9672,36.7737,36.9889,36.6363,36.369,36.217,36.5701,
                36.4,36.0297,35.99,35.8822,35.8185,35.5571,35.1871,35.1698,35.1016,34.8423,34.8138,34.7362,34.6833,
                34.3927,34.472,33.5109,33.2833,33.2428,35.2054,37.7046,37.4858,37.2611,38.0573,37.6808,37.1675,
                37.1564,36.4847,36.7767,36.3243,36.2694,36.1027,35.7265,35.6092,35.5601,35.4023,35.6539,34.6856,
                34.5504,34.6151,36.9407,36.8689,36.6243,36.5304,36.3531,36.1276,35.9744,35.6682,35.562,35.4885,
                35.4099,34.8851,34.8135
        };

        Double ObserX[] = {128.567,127.3063,127.0628,126.7625,128.761,127.7379,124.6167,128.8933,129.1266,126.96579,
                126.6266,127.9504,130.89999,126.9875,128.4597,127.9522,126.4959,129.4153,127.4428,127.3742,127.9967,
                128.7095,128.14999,129.382,126.7078,128.6212,127.1571,129.32249,128.56731,126.8936,129.0343,128.43781,
                126.3832,127.7428,125.45,126.7039,126.3236,126.5317,126.1667,126.5674,128.12131,126.4486,127.4966,
                127.4863,128.1692,127.8826,128.9915,128.1965,127.7363,127.1213,126.5594,126.9229,127.4839,126.7186,
                127.2877,126.8682,127.3351,127.5224,126.9216,126.5711,127.2778,128.91679,128.5191,128.151,129.4117,
                128.6908,128.3228,128.9537,127.9132,128.172,128.7464,127.8813,128.6067,127.9286
        };

        Double min_dist = Double.parseDouble("987654321"); // 최단 거리 구하기
        int min_code = 0;
        for(int i = 0; i < ObserX.length; i++){
            // 유클리드 거리 계산
            Double dist = Math.sqrt(Math.pow(ObserX[i] - x, 2) + Math.pow(ObserY[i] - y, 2));
            if(dist < min_dist){
                min_dist = dist;
                min_code = ObserCode[i];
            }
        }
        return Integer.toString(min_code);
    }

    public GetWeatherRandomRes randomData(String date, String place, String userIdx) throws BaseException {
        List<GetWeatherRes> weatherList = weatherSearch(date, place); // 날씨 정보 가져옴

        // 해당 유저가 등록한 리스트 가져옴
        User user = userRepository.findByUserIdx(Long.parseLong(userIdx)); // 유저 검색
        List<Post> userPostList = postRepository.findAllByUser(user); // 해당 user post 검색
        // 랜덤으로 섞기
        Collections.shuffle(userPostList);

        // 오늘 날씨와 같은 post 찾음
        for(int i = 0; i < userPostList.size(); i++){
            Post post = userPostList.get(i);
            List<String> getWeatherList = post.getWeather();
            for(int j = 0; j < getWeatherList.size(); j++){
                // 날씨와 같으면 출력
                if(getWeatherList.get(j).equals(weatherList.get(0).getWeather())){
                    GetWeatherRandomRes getWeatherRandomRes = GetWeatherRandomRes.builder()
                            .date(weatherList.get(0).getDate())
                            .weather(weatherList.get(0).getWeather())
                            .temp(weatherList.get(0).getTemp())
                            .artist(post.getMusic().getArtist())
                            .track(post.getMusic().getTrack())
                            .build();
                    return getWeatherRandomRes;
                }

                // 더움, 추움과 같으면 출력
                String temp = "";
                if(weatherList.get(0).getTemp() == 1)
                    temp = "HOT";
                else if(weatherList.get(0).getTemp() == 2)
                    temp = "COLD";
                if(getWeatherList.get(j).equals(temp)){
                    GetWeatherRandomRes getWeatherRandomRes = GetWeatherRandomRes.builder()
                            .date(weatherList.get(0).getDate())
                            .weather(getWeatherList.get(j))
                            .temp(weatherList.get(0).getTemp())
                            .artist(post.getMusic().getArtist())
                            .track(post.getMusic().getTrack())
                            .build();
                    return getWeatherRandomRes;
                }
            }
        }

        GetWeatherRandomRes getWeatherRandomRes = GetWeatherRandomRes.builder()
                .date(weatherList.get(0).getDate())
                .weather(weatherList.get(0).getWeather())
                .temp(weatherList.get(0).getTemp())
                .artist("")
                .track("")
                .build();
        return getWeatherRandomRes;
    }

    public List<GetWeatherRes> weatherSearch(String date, String place) throws BaseException {
        List<GetWeatherRes> result = new ArrayList<>();

        try{
            String placeCode = getNearObservatory(place); // 장소 코드

            // url (현재 기능상 시작, 종료날짜 통일)
            String url = "http://apis.data.go.kr/1360000/AsosDalyInfoService/getWthrDataList?serviceKey=" + apikey
                    + "&numOfRows=10&pageNo=1&dataCd=ASOS&dateCd=DAY&startDt=" + date + "&endDt=" + date + "&stnIds=" + placeCode;

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
//                System.out.println("날짜 : " + getTagValue("tm", eElement));
//                System.out.println("평균 기온 : " + getTagValue("avgTa", eElement));
//                System.out.println("최고 기온 : " + getTagValue("maxTa", eElement));
//                System.out.println("일기현상 : " + getTagValue("iscs", eElement));

                GetWeatherRes item = new GetWeatherRes(date, "", 0);

                // 날씨 구분
                String cloud = getTagValue("avgTca", eElement);
                String weather = getTagValue("iscs", eElement);
                if(weather.indexOf("눈") != -1)
                    item.setWeather("SNOW");
                else if(weather.indexOf("비") != -1 || weather.indexOf("소나기") != -1)
                    item.setWeather("RAIN");
                // 전운량 7이상이면 흐림 처리 (기상청 기준 0~5 맑음, 6~8 구름많음, 9~10 흐림)
                else if(Float.parseFloat(cloud) >= 7)
                    item.setWeather("CLOUD");
                else item.setWeather("SUNNY");

                // 더움 추분 구분
                String maxTemp = getTagValue("maxTa", eElement);
                String avgTemp = getTagValue("avgTa", eElement);
                if(Float.parseFloat(maxTemp) >= 30)
                    item.setTemp(1);
                else if(Float.parseFloat(avgTemp) < 0)
                    item.setTemp(2);

                result.add(item);
            }

        } catch (Exception e){
            e.printStackTrace();
        }

        return result;
    }

}