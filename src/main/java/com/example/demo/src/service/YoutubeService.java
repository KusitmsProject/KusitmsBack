package com.example.demo.src.service;

import com.example.demo.config.secret.Secret;
import com.example.demo.src.dto.response.BaseException;
import com.example.demo.src.dto.response.GetYouTubeRes;
import com.example.demo.src.entity.Music;
import com.example.demo.src.repository.MusicRepository;
import com.google.api.client.googleapis.json.GoogleJsonResponseException;
import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpRequestInitializer;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.youtube.YouTube;
import com.google.api.services.youtube.model.SearchListResponse;
import com.google.api.services.youtube.model.SearchResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class YoutubeService {


    private static MusicRepository musicRepository;
    @Autowired
    public YoutubeService( MusicRepository musicRepository){
        this.musicRepository = musicRepository;
    }


    //private static Logger log = (Logger) LoggerFactory.getLogger(YouTube.Search.class);
    private static final HttpTransport HTTP_TRANSPORT = new NetHttpTransport();
    private static final JsonFactory JSON_FACTORY = new JacksonFactory();
    private static final long NUMBER_OF_VIDEOS_RETURNED = 1;  // 검색 개수
    private static final String GOOGLE_YOUTUBE_URL =  "https://www.youtube.com/watch?v=";
//    private static final String YOUTUBE_SEARCH_FIELDS = "items(id/kind,id/videoId,snippet/title," +
//            "snippet/description,snippet/channelTitle,snippet/thumbnails/default/url)";
    private static final String YOUTUBE_SEARCH_FIELDS = "items(id/videoId,snippet/title)";
    private static final String YOUTUBE_APIKEY = Secret.youtube_api;


    private static YouTube youtube;

    public static String videoId=null;

    static {
        youtube = new YouTube.Builder(HTTP_TRANSPORT, JSON_FACTORY, new HttpRequestInitializer() {
            public void initialize(HttpRequest request) throws IOException {
            }
        }).setApplicationName("youtube-cmdline-search-sample").build();
    }

    public static List<GetYouTubeRes> youTubeSearch(String track,String artist,String trackIdx,String searchQuery, int maxSearch) throws BaseException {
        String queryTerm = searchQuery;
        //log.info("Starting YouTube search... " +queryTerm);
        List<GetYouTubeRes> rvalue = new ArrayList<GetYouTubeRes>();

        try {
            if (youtube != null) {
                // Define the API request for retrieving search results.
                YouTube.Search.List search = youtube.search().list("id,snippet");

                String apiKey = YOUTUBE_APIKEY;
                search.setKey(apiKey);
                search.setQ(queryTerm);
                search.setType("video");
                String youTubeFields = YOUTUBE_SEARCH_FIELDS;
                search.setMaxResults(NUMBER_OF_VIDEOS_RETURNED);

                if (youTubeFields != null && !youTubeFields.isEmpty()) {
                    search.setFields(youTubeFields);
                } else {
                    search.setFields(YOUTUBE_SEARCH_FIELDS);
                }

                SearchListResponse searchResponse = search.execute();
                List<SearchResult> searchResultList = searchResponse.getItems();

                if (searchResultList != null) {
                    for (SearchResult rid : searchResultList) {
                        videoId=rid.getId().getVideoId();
                        GetYouTubeRes item=new GetYouTubeRes(rid.getId().getVideoId(),
                                rid.getSnippet().getTitle(),GOOGLE_YOUTUBE_URL + rid.getId().getVideoId());

                        rvalue.add(item);
                        //log.info("title : " + rid.getSnippet().getTitle());
                    }
                }
            }

        } catch (GoogleJsonResponseException e){
            System.err.println("There was a service error: " + e.getDetails().getCode() + " : "
                    + e.getDetails().getMessage());
        } catch(IOException e){
            System.err.println("There was an IO error: " + e.getCause() + " : " + e.getMessage());
        } catch(Throwable t){
            t.printStackTrace();
        }

        Music music=Music.builder()
                        .track(track)
                        .trackIdx(trackIdx)
                        .artist(artist)
                        .videoIdx(videoId)
                        .build();

        //만약 track이 있다면 (track의 사이즈가 0이 아니라면) 저장하지 않을 것

        List<Music> trackRepo=musicRepository.findByTrack(track);
        List<Music> artistRepo=musicRepository.findByArtist(artist);
        if(trackRepo.size()==0||artistRepo.size()==0){
            musicRepository.save(music);
        }


        return rvalue;
    }
}
