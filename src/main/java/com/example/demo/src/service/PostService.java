package com.example.demo.src.service;

import com.example.demo.src.dto.request.PostPostingReq;
import com.example.demo.src.dto.response.BaseException;
import com.example.demo.src.dto.response.PostPostingRes;
import com.example.demo.src.entity.Post;
import com.example.demo.src.repository.MusicRepository;
import com.example.demo.src.repository.PostRepository;
import com.example.demo.src.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
        // todo : musicrepo에서 music 가져오기
        Post post = Post.builder()
                .user(userRepository.findByUserIdx(postPostingReq.getUserIdx()))
                .date(postPostingReq.getDate())
                .emotion(postPostingReq.getEmotion())
                .music(musicRepository.findByTrack(postPostingReq.getTrack()))
//                .weather(postPostingReq.getWeather())
                .season(postPostingReq.getSeason())
                .place(postPostingReq.getPlace())
                .imageUrl(postPostingReq.getImageUrl())
                .record(postPostingReq.getRecord())
//                .friendList(postPostingReq.getFriendList())
                .options(postPostingReq.getOptions())
                .build();

        Post savedPost = postRepository.save(post);
        PostPostingRes postPostingRes = PostPostingRes.builder()
                .postIdx(savedPost.getPostIdx())
                .build();

        return postPostingRes;
    }
}
