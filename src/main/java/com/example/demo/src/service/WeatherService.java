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
        String Observatories[] = {"속초","철원","동두천","파주","대관령","춘천","백령도","강릉","동해",
                "서울","인천","원주","울릉도","홍천","태백","정선","제천","보은","천안","보령","부여","금산",
                "세종","부안","임실","정읍","남원","장수","수원","영월","충주","서산","울진","청주","대전",
                "추풍령","안동","상주","포항","군산","대구","전주","울산","창원","광주","부산","고창",
                "영광","김해","순창","양산","보성","강진","장흥","해남","고흥","의령","함양",
                "광양","진도","봉화","영주","문경","통영","목포","여수","흑산도","완도","고창","순천",
                "홍성","제주","고산","성산","서귀포","진주","강화","양평","이천","인제","청송","영덕",
                "의성","구미","영천","경주","거창","합천","밀양","산청","거제","남해"};

        String ObserNum[] = {"90","95","98","99","100","101","102","105","106","108",
                "112","114","115","212","216","217","221","226","232","235","236","238",
                "239","243","244","245","247","248","119","121","127","129","130","131",
                "133","135","136","137","138","140","143","146","152","155","156","159",
                "251","252","253","254","257","258","259","260","261","262","263","264",
                "266","268","271","272","273","162","165","168","169","170","172","174",
                "177","184","185","188","189","192","201","202","203","211","276","277",
                "278","279","281","283","284","285","288","289","294","295"};

        String[] inputs = place.split(" ");

        for(int i = 0; i < inputs.length; i++){
            if(i > 1) break; // 시군 단위까지 검색
            for(int j = 0; j < Observatories.length; j++){
                String str = inputs[i];

                // todo : 시, 광역시, 군 등 처리
//                String str = inputs[i].substring(0, inputs[i].length()-1);
                if(str.indexOf(Observatories[j]) != -1)
                    return ObserNum[j];
            }
        }

        return "";
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
            String test = getNearObservatory(place);
            System.out.println("test = " + test);
            String placeNum = "108"; // 장소 번호

            // url (현재 기능상 시작, 종료날짜 통일)
            String url = "http://apis.data.go.kr/1360000/AsosDalyInfoService/getWthrDataList?serviceKey=" + apikey
                    + "&numOfRows=10&pageNo=1&dataCd=ASOS&dateCd=DAY&startDt=" + date + "&endDt=" + date + "&stnIds=" + placeNum;

            System.out.println("url = " + url);
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