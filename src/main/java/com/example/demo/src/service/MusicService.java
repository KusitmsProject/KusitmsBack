package com.example.demo.src.service;



import com.example.demo.src.entity.Music;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.springframework.stereotype.Service;

import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.List;

@Service
public class MusicService {

    private static String URL = "";
    private static final String GET = "GET";
    private static final String USER_AGENT = "Mozilla/5.0";

    String artist="";
    String realTrack="";
    String trackIdx="";

    public List<Music> parsingSearchTrack(String track) throws IOException {
        //track을 인코딩 해야함
        String encodeData="";
        encodeData = URLEncoder.encode(track, "UTF-8");

        URL=String.format("http://3.34.31.255:8081/bring/spotify?track=%s",encodeData);


        System.out.println(URL);

        URL url = new URL(URL);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();

        connection.setRequestMethod(GET);
        connection.setRequestProperty("User-Agent", USER_AGENT);

        int responseCode = connection.getResponseCode();
        System.out.println(responseCode);
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        StringBuffer stringBuffer = new StringBuffer();
        String inputLine;

        while ((inputLine = bufferedReader.readLine()) != null)  {
            stringBuffer.append(inputLine);
        }
        bufferedReader.close();

        String response = stringBuffer.toString();


        //Gson 라이브러리에 포함된 클래스로 JSON파싱 객체 생성
        JsonParser jsonParser=new JsonParser();
        JsonElement element= jsonParser.parse(response);
        JsonObject result=element.getAsJsonObject().get("result").getAsJsonObject();

        JsonArray items= result.get("items").getAsJsonArray();
        //여기서 foreach 돌리면 될 듯 List<>한다면

        List<Music> spotifyList = new ArrayList<>();


        //페이징을 3개라 가정
        for(int i=0;i<3;i++){
            JsonObject tracks= items.get(i).getAsJsonObject();
            trackIdx=tracks.get("id").getAsString();
            realTrack=tracks.get("name").getAsString();


            JsonObject album=tracks.getAsJsonObject("album");
            JsonArray artists=album.getAsJsonArray("artists");
            JsonObject artistsObject= (JsonObject) artists.get(0);
            artist=artistsObject.get("name").getAsString();

            System.out.println(artist);
            System.out.println(realTrack);

            Music music=Music.builder()
                            .trackIdx(trackIdx)
                            .track(realTrack)
                            .artist(artist)
                            .build();

           spotifyList.add(music);



        }



        return spotifyList;



    }

    public List<String> searchLyrics(String trackIdx) throws IOException {
        String lyricURL=String.format("https://spotify-lyric-api.herokuapp.com/?trackid=%s",trackIdx);
        URL url2 = new URL(lyricURL);
        HttpURLConnection connection = (HttpURLConnection) url2.openConnection();

        connection.setRequestMethod(GET);
        connection.setRequestProperty("User-Agent", USER_AGENT);

        int responseCode = connection.getResponseCode();
        System.out.println(responseCode);
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        StringBuffer stringBuffer = new StringBuffer();
        String inputLine;

        while ((inputLine = bufferedReader.readLine()) != null)  {
            stringBuffer.append(inputLine);
        }
        bufferedReader.close();

        String response = stringBuffer.toString();


        //Gson 라이브러리에 포함된 클래스로 JSON파싱 객체 생성
        JsonParser jsonParser=new JsonParser();
        JsonElement element= jsonParser.parse(response);
        JsonArray lines=element.getAsJsonObject().get("lines").getAsJsonArray();


        List<String>lyricsList=new ArrayList<>();
        for(int i=0;i<lines.size();i++){
            JsonObject object=lines.get(i).getAsJsonObject();
            String words=object.get("words").getAsString();
            lyricsList.add(words);

        }

        return lyricsList;







    }



}
