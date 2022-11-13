package com.example.demo.src.service;

import com.example.demo.src.dto.request.PostPostingReq;
import com.example.demo.src.dto.response.BaseException;
import com.example.demo.src.dto.response.PostPostingRes;
import com.example.demo.src.entity.Music;
import com.example.demo.src.entity.Post;
import com.example.demo.src.repository.MusicRepository;
import com.example.demo.src.repository.PostRepository;
import com.example.demo.src.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class PostService {
    private final UserRepository userRepository;
    private final PostRepository postRepository;

    private final MusicRepository musicRepository;

    @Autowired
    public PostService(UserRepository userRepository, PostRepository postRepository, MusicRepository musicRepository){
        this.userRepository = userRepository;
        this.postRepository = postRepository;
        this.musicRepository = musicRepository;
    }

    public PostPostingRes register(PostPostingReq postPostingReq) throws BaseException {
        //jungmin
        // 이문세의 붉은 노을 빅뱅의 붉은 노을을 구별해야함
        // track으로 찾아온 Music 의 artist와 artist로 찾아온 Music의 artist가 같으면 저장

        List<Music> findByTrack = musicRepository.findByTrack(postPostingReq.getTrack());
        List<Music>ans=findByTrack.stream().filter(
                o->o.getArtist().equals(postPostingReq.getArtist())
        ).collect(Collectors.toList());
        Music music=ans.get(0);//filter한 값 중 가장 앞에 값을 music에 넣자

        Post post = Post.builder()
                .user(userRepository.findByUserIdx(postPostingReq.getUserIdx()))
                .date(postPostingReq.getDate())
                .emotion(postPostingReq.getEmotion())
                .music(music)
                .weather(postPostingReq.getWeather())
                .season(postPostingReq.getSeason())
                .lyrics(postPostingReq.getLyrics())
                .placeNickname(postPostingReq.getPlaceNickname())
                .place(postPostingReq.getPlace())
                .imageUrl(postPostingReq.getImageUrl())
                .record(postPostingReq.getRecord())
                .friendList(postPostingReq.getFriendList())
                .options(postPostingReq.getOptions())
                .build();

        Post savedPost = postRepository.save(post);
        PostPostingRes postPostingRes = PostPostingRes.builder()
                .postIdx(savedPost.getPostIdx())
                .build();

        return postPostingRes;
    }
}
