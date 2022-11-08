package com.example.demo.src.spotify;

import com.example.demo.config.secret.Secret;
import org.springframework.stereotype.Service;
import se.michaelthelin.spotify.SpotifyApi;
import se.michaelthelin.spotify.exceptions.SpotifyWebApiException;
import se.michaelthelin.spotify.model_objects.credentials.ClientCredentials;
import se.michaelthelin.spotify.requests.authorization.client_credentials.ClientCredentialsRequest;

import java.io.IOException;

@Service
public class SpotifyToken {
    private static final String clientId = Secret.spotify_client_id ;
    private static final String clientSecret = Secret.spotify_client_secret;
    private static SpotifyApi spotifyApi = new SpotifyApi.Builder().setClientId(clientId)
            .setClientSecret(clientSecret).build();



    public String getAccessToken() {

        ClientCredentialsRequest clientCredentialsRequest = spotifyApi.clientCredentials().build();
        try {

            final ClientCredentials clientCredentials = clientCredentialsRequest.execute();
            // Set access token for further "spotifyApi" object usage
            spotifyApi.setAccessToken(clientCredentials.getAccessToken());



            System.out.println("Expires in: " + clientCredentials.getExpiresIn());

            return spotifyApi.getAccessToken();


        } catch (IOException | SpotifyWebApiException e) {
            System.out.println("Error: " + e.getMessage());
        } catch (org.apache.hc.core5.http.ParseException e) {
            throw new RuntimeException(e);
        }

        return spotifyApi.getAccessToken();


    }

}
