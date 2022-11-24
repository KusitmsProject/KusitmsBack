package com.example.demo.src.service;



import com.example.demo.src.dto.response.BaseException;
import com.example.demo.src.dto.response.GetSpotifyRes;
import com.example.demo.src.entity.Music;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.springframework.stereotype.Service;
import se.michaelthelin.spotify.model_objects.specification.ArtistSimplified;
import se.michaelthelin.spotify.model_objects.specification.Track;

import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class MusicService {

    private static String URL = "";
    private static final String GET = "GET";
    private static final String USER_AGENT = "Mozilla/5.0";


    public List<Music> parsingSearchTrack(Track[] trackArray) throws IOException,BaseException {

        // 여기부터 service단으로 이동

        //노래 이름만 담기
        List<String>trackName= Arrays.stream(trackArray).map(
                o->o.getName()
        ).collect(Collectors.toList());

        //아티스트 이름만 담기
        List<ArtistSimplified[]>artistSimplifiedList= Arrays.stream(trackArray).map(
                o->o.getArtists()
        ).collect(Collectors.toList());
        List<ArtistSimplified> artistSimplified=new ArrayList<>();
        for(int i=0;i<4;i++){
            artistSimplified.add(artistSimplifiedList.get(i)[0]);
        }
        List<String>artistList=artistSimplified.stream().map(
                o->o.getName()
        ).collect(Collectors.toList());

        //trackIdx만 담기
        List<String>trackIdxList= Arrays.stream(trackArray).map(
                o->o.getId()
        ).collect(Collectors.toList());



        List<Music> spotifyList = new ArrayList<>();


        //페이징을 3개라 가정
        for(int i=0;i<4;i++){


            Music music=Music.builder()
                            .trackIdx(trackIdxList.get(i))
                            .track(trackName.get(i))
                            .artist(artistList.get(i))
                            .build();

           spotifyList.add(music);



        }



        return spotifyList;



    }

    public List<String> searchLyrics(String trackIdx) throws IOException, BaseException {
        String lyricURL=String.format("https://spotify-lyric-api.herokuapp.com/?trackid=%s",trackIdx);
        URL url = new URL(lyricURL);
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
