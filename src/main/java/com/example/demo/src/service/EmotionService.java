package com.example.demo.src.service;

import com.example.demo.src.dto.response.BaseException;
import com.example.demo.src.dto.response.GetSearchEmotionRes;
import com.example.demo.src.dto.response.GetSearchTrackRes;
import com.example.demo.src.entity.Music;
import com.example.demo.src.entity.Post;
import com.example.demo.src.entity.User;
import com.example.demo.src.repository.MusicRepository;
import com.example.demo.src.repository.PostRepository;
import com.example.demo.src.repository.UserRepository;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class EmotionService {
    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final MusicRepository musicRepository;

    private static String URL = "";
    private static final String GET = "GET";
    private static final String USER_AGENT = "Mozilla/5.0";

    @Autowired
    public EmotionService(PostRepository postRepository, UserRepository userRepository, MusicRepository musicRepository) {
        this.postRepository = postRepository;
        this.userRepository = userRepository;
        this.musicRepository = musicRepository;
    }

    public List<GetSearchEmotionRes> searchForEmotion(Long userIdx, String emotion) throws BaseException, IOException {
        List<GetSearchEmotionRes> result = new ArrayList<>();

        User user = userRepository.findByUserIdx(userIdx);
        List<Post> postList = postRepository.findAllByUserAndEmotion(user, emotion);

        for(int i = 0; i < postList.size(); i++){
            // 해당 post의 가수, 제목 찾기
            Music music = postList.get(i).getMusic();

            //우선 스포티파이 id 가져온다
            String encodeData="";
            encodeData = URLEncoder.encode(music.getTrack(), "UTF-8");

            URL=String.format("http://3.34.31.255:8081/bring/spotify/track?track=%s",encodeData);


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
            JsonArray results=element.getAsJsonObject().get("result").getAsJsonArray();
            JsonObject targetItem=results.get(0).getAsJsonObject();
            String trackIdx=targetItem.get("trackIdx").getAsString();

            // 가사 api에 id 넣는다
            String encodeData2="";
            encodeData2 = URLEncoder.encode(trackIdx, "UTF-8");

            URL=String.format("http://3.34.31.255:8081/bring/spotify/lyrics?trackid=%s",encodeData2);


            URL url2 = new URL(URL);
            HttpURLConnection connection2 = (HttpURLConnection) url2.openConnection();

            connection2.setRequestMethod(GET);
            connection2.setRequestProperty("User-Agent", USER_AGENT);


            BufferedReader bufferedReader2 = new BufferedReader(new InputStreamReader(connection2.getInputStream()));
            StringBuffer stringBuffer2 = new StringBuffer();
            String inputLine2;

            while ((inputLine2 = bufferedReader2.readLine()) != null)  {
                stringBuffer2.append(inputLine2);
            }
            bufferedReader2.close();

            String response2 = stringBuffer2.toString();

            JsonParser jsonParser2=new JsonParser();
            JsonElement element2= jsonParser2.parse(response2);
            JsonArray results2=element2.getAsJsonObject().get("result").getAsJsonArray();

            // 4줄씩 파싱을 진행한다.
            String lyrics=null;
            for(int num=0;num<4;num++){
                lyrics+=results2.get(num).getAsString().concat("\n");
            }





            GetSearchEmotionRes getSearchEmotionRes = GetSearchEmotionRes.builder()
                    .postIdx(postList.get(i).getPostIdx())
                    .musicIdx(music.getMusicIdx())
                    .date(postList.get(i).getDate())
                    .artist(music.getArtist())
                    .track(music.getTrack())
                    .lyrics(lyrics)
                    .options(postList.get(i).getOptions())
                    .build();
            result.add((getSearchEmotionRes));
        }
        return result;
    }

    public List<GetSearchTrackRes> searchForTrack(Long userIdx, String musicIdx) throws BaseException, IOException {
        List<GetSearchTrackRes> result = new ArrayList<>();

        User user = userRepository.findByUserIdx(userIdx);
        Music music = musicRepository.findByMusicIdx(Long.parseLong(musicIdx));

        //우선 스포티파이 id 가져온다
        String encodeData="";
        encodeData = URLEncoder.encode(music.getTrack(), "UTF-8");

        URL=String.format("http://3.34.31.255:8081/bring/spotify/track?track=%s",encodeData);


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
        JsonArray results=element.getAsJsonObject().get("result").getAsJsonArray();
        JsonObject targetItem=results.get(0).getAsJsonObject();
        String trackIdx=targetItem.get("trackIdx").getAsString();



        // 가사 api에 id 넣는다
        String encodeData2="";
        encodeData2 = URLEncoder.encode(trackIdx, "UTF-8");

        URL=String.format("http://3.34.31.255:8081/bring/spotify/lyrics?trackid=%s",encodeData2);


        URL url2 = new URL(URL);
        HttpURLConnection connection2 = (HttpURLConnection) url2.openConnection();
        connection2.setRequestMethod(GET);
        connection2.setRequestProperty("User-Agent", USER_AGENT);

        BufferedReader bufferedReader2 = new BufferedReader(new InputStreamReader(connection2.getInputStream()));
        StringBuffer stringBuffer2 = new StringBuffer();
        String inputLine2;

        while ((inputLine2 = bufferedReader2.readLine()) != null)  {
            stringBuffer2.append(inputLine2);
        }
        bufferedReader2.close();

        String response2 = stringBuffer2.toString();
        JsonParser jsonParser2=new JsonParser();
        JsonElement element2= jsonParser2.parse(response2);
        JsonArray results2=element2.getAsJsonObject().get("result").getAsJsonArray();

        // 4줄씩 파싱을 진행한다.
        String lyrics=null;
        for(int num=0;num<4;num++){
            lyrics+=results2.get(num).getAsString().concat("\n");
        }

        List<Post> postList = postRepository.findAllByUserAndMusic(user, music);
        for(int i = 0; i < postList.size(); i++){
            // 해당 post의 가수, 제목 찾기
            GetSearchTrackRes getSearchTrackRes = GetSearchTrackRes.builder()
                    .postIdx(postList.get(i).getPostIdx())
                    .musicIdx(music.getMusicIdx())
                    .date(postList.get(i).getDate())
                    .videoId(music.getVideoIdx())
                    .artist(music.getArtist())
                    .track(music.getTrack())
                    .lyrics(lyrics)
                    .emotion(postList.get(i).getEmotion())
                    .options(postList.get(i).getOptions())
                    .build();
            result.add((getSearchTrackRes));
        }
        return result;
    }

    public List<GetSearchTrackRes> search(Long userIdx, String input) throws BaseException{
        List<GetSearchTrackRes> result = new ArrayList<>();

        User user = userRepository.findByUserIdx(userIdx); // 유저 검색
        List<Post> userPostList = postRepository.findAllByUser(user); // 해당 user post 검색
        List<Music> musicList = musicRepository.findAllByArtistLikeOrTrackLike("%" + input + "%", "%" + input + "%"); // 음악, 아티스트 검색

        for(int i = 0; i < musicList.size(); i++){
            System.out.println("musicList = " + musicList.get(i).getMusicIdx());
        }

        // 해당 음악id와 post의 음악id 일치하면 추가
        for(int i = 0; i < musicList.size(); i++){
            for(int j = 0; j < userPostList.size(); j++){
                if(musicList.get(i).getMusicIdx() == userPostList.get(j).getMusic().getMusicIdx()){
                    GetSearchTrackRes getSearchTrackRes = GetSearchTrackRes.builder()
                            .postIdx(userPostList.get(j).getPostIdx())
                            .musicIdx(musicList.get(i).getMusicIdx())
                            .artist(musicList.get(i).getArtist())
                            .track(musicList.get(i).getTrack())
                            .emotion(userPostList.get(j).getEmotion())
                            .date(userPostList.get(j).getDate())
                            .options(userPostList.get(j).getOptions())
                            .build();
                    result.add(getSearchTrackRes);
                }
            }
        }

        return result;
    }
}
