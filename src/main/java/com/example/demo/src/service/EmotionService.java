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
        List<Post> postList = postRepository.findAllByUserAndEmotion(user, emotion);
        for(int i = 0; i < postList.size(); i++){
            // 해당 post의 가수, 제목 찾기
            Music music = postList.get(i).getMusic();

            GetSearchEmotionRes getSearchEmotionRes = GetSearchEmotionRes.builder()
                    .postIdx(postList.get(i).getPostIdx())
                    .musicIdx(music.getMusicIdx())
                    .date(postList.get(i).getDate())
                    .artist(music.getArtist())
                    .track(music.getTrack())
                    .options(postList.get(i).getOptions())
                    .build();
            result.add((getSearchEmotionRes));
        }
        return result;
    }

    public List<GetSearchTrackRes> searchForTrack(String userIdx, String musicIdx) throws BaseException{
        List<GetSearchTrackRes> result = new ArrayList<>();

        User user = userRepository.findByUserIdx(Long.parseLong(userIdx));
        Music music = musicRepository.findByMusicIdx(Long.parseLong(musicIdx));
        List<Post> postList = postRepository.findAllByUserAndMusic(user, music);
        for(int i = 0; i < postList.size(); i++){
            // 해당 post의 가수, 제목 찾기
            GetSearchTrackRes getSearchTrackRes = GetSearchTrackRes.builder()
                    .postIdx(postList.get(i).getPostIdx())
                    .musicIdx(music.getMusicIdx())
                    .date(postList.get(i).getDate())
                    .artist(music.getArtist())
                    .track(music.getTrack())
                    .emotion(postList.get(i).getEmotion())
                    .options(postList.get(i).getOptions())
                    .build();
            result.add((getSearchTrackRes));
        }
        return result;
    }

    public List<GetSearchTrackRes> search(String userIdx, String input) throws BaseException{
        List<GetSearchTrackRes> result = new ArrayList<>();

        User user = userRepository.findByUserIdx(Long.parseLong(userIdx)); // 유저 검색
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
