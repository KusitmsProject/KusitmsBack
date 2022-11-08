package com.example.demo.src.spotify;


import com.example.demo.src.service.MusicService;
import com.neovisionaries.i18n.CountryCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import se.michaelthelin.spotify.SpotifyApi;
import se.michaelthelin.spotify.exceptions.SpotifyWebApiException;
import se.michaelthelin.spotify.model_objects.specification.Paging;
import se.michaelthelin.spotify.model_objects.specification.Track;
import se.michaelthelin.spotify.requests.data.search.simplified.SearchTracksRequest;

import java.io.IOException;

@Service
public class SearchTrack {



    public static Paging<Track> searchTracks(String accessToken,String track) {

        System.out.println("searchTrack");

        SpotifyApi spotifyApi = new SpotifyApi.Builder()
                .setAccessToken(accessToken)
                .build();

        SearchTracksRequest searchTracksRequest = spotifyApi.searchTracks(track)
                .limit(3) //페이징
          .market(CountryCode.KR)
//          .limit(10)
//          .offset(0)
//          .includeExternal("audio")
                .build();
        Paging<Track> trackPaging = null;
        try {
            trackPaging = searchTracksRequest.execute();




            return trackPaging;

        } catch (IOException | SpotifyWebApiException | org.apache.hc.core5.http.ParseException e) {
            System.out.println("Error: " + e.getMessage());
        }
        return trackPaging;
    }



}
