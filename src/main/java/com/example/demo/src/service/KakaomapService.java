package com.example.demo.src.service;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

@Service
@PropertySource("classpath:kakao.properties")
public class KakaomapService {
    private static String kakao_apikey;

    @Value("${apikey}")
    public void setApiKey(String key) {kakao_apikey = key;}

    // 해당 주소 좌표 반환
    public List<String> getCoordinates(String place) {
        List<String> result = new ArrayList<>();
        try {
            // UTF-8로 인코딩
            String query = URLEncoder.encode(place, "UTF-8");

            // 파라미터를 사용하여 요청 URL을 구성한다.
            String apiURL = "https://dapi.kakao.com/v2/local/search/address.JSON?" +
                    "query=" + query
//                    + "analyze_type=" + "similar"
                    ;
            URL url = new URL(apiURL);
            HttpURLConnection con = (HttpURLConnection)url.openConnection();

            // 요청 헤더를 setRequestProperty로 지정해준다. 헤더가 더 많을시 더 추가하면 됨.
            con.setRequestProperty("Authorization", "KakaoAK " + kakao_apikey);
            con.setRequestMethod("GET");

            // 응답 코드 확인
            int responseCode = con.getResponseCode();
            BufferedReader br;

            // 정상 응답이 200이므로(http 상태코드)
            if(responseCode == 200) { // 정상 호출
                br = new BufferedReader(new InputStreamReader(con.getInputStream()));
            } else {  // 에러 발생
                br = new BufferedReader(new InputStreamReader(con.getErrorStream()));
            }

            String inputLine;
            StringBuffer response = new StringBuffer();
            while ((inputLine = br.readLine()) != null) {
                response.append(inputLine);
            }
            br.close();
//            System.out.println(response.toString());
            String str = response.toString();
            JsonParser jsonParser=new JsonParser();
            JsonElement element= jsonParser.parse(str);
            JsonArray jsonArray = element.getAsJsonObject().get("documents").getAsJsonArray();
            System.out.println("jsonArray = " + jsonArray);

            // todo : null처리
            JsonObject jsonObject = (JsonObject) jsonArray.get(0);//인덱스 번호로 접근해서 가져온다.

            String x = jsonObject.get("x").getAsString();
            String y = jsonObject.get("y").getAsString();
            System.out.println("x = " + x);
            System.out.println("y = " + y);

            result.add(x);
            result.add(y);

            return result;
        } catch (Exception e) {
            System.out.println(e);
        }
        return result;
    }
}