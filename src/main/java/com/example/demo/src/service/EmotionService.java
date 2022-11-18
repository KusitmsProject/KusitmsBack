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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class EmotionService {
    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final MusicRepository musicRepository;

    @Autowired
    public EmotionService(PostRepository postRepository, UserRepository userRepository, MusicRepository musicRepository) {
        this.postRepository = postRepository;
        this.userRepository = userRepository;
        this.musicRepository = musicRepository;
    }

    public List<GetSearchEmotionRes> searchForEmotion(String userIdx, String emotion) throws BaseException{
        List<GetSearchEmotionRes> result = new ArrayList<>();

        User user = userRepository.findByUserIdx(Long.parseLong(userIdx));

        List<Post> test = postRepository.findAllByUserAndEmotion(user, emotion);
        for(int i = 0; i < test.size(); i++){
            // 해당 post의 가수, 제목 찾기
            Music music = test.get(i).getMusic();

            GetSearchEmotionRes getSearchEmotionRes = GetSearchEmotionRes.builder()
                    .postIdx(test.get(i).getPostIdx())
                    .musicIdx(music.getMusicIdx())
                    .date(test.get(i).getDate())
                    .artist(music.getArtist())
                    .track(music.getTrack())
                    .options(test.get(i).getOptions())
                    .build();
            result.add((getSearchEmotionRes));
        }
        return result;
    }

    public List<GetSearchTrackRes> searchForTrack(String userIdx, String musicIdx) throws BaseException{
        List<GetSearchTrackRes> result = new ArrayList<>();

        User user = userRepository.findByUserIdx(Long.parseLong(userIdx));
        Music music = musicRepository.findByMusicIdx(Long.parseLong(musicIdx));

        List<Post> test = postRepository.findAllByUserAndMusic(user, music);
        for(int i = 0; i < test.size(); i++){
            // 해당 post의 가수, 제목 찾기
            GetSearchTrackRes getSearchTrackRes = GetSearchTrackRes.builder()
                    .postIdx(test.get(i).getPostIdx())
                    .musicIdx(music.getMusicIdx())
                    .date(test.get(i).getDate())
                    .artist(music.getArtist())
                    .track(music.getTrack())
                    .emotion(test.get(i).getEmotion())
                    .options(test.get(i).getOptions())
                    .build();
            result.add((getSearchTrackRes));
        }
        return result;
    }
}
