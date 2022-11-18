package com.example.demo.src.service;

import com.example.demo.src.dto.response.BaseException;
import com.example.demo.src.dto.response.GetEmotionRes;
import com.example.demo.src.entity.Post;
import com.example.demo.src.entity.User;
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

    @Autowired
    public EmotionService(PostRepository postRepository, UserRepository userRepository) {
        this.postRepository = postRepository;
        this.userRepository = userRepository;
    }

    public List<GetEmotionRes> search(String userIdx, String emotion) throws BaseException{
        List<GetEmotionRes> result = new ArrayList<>();

        User user = userRepository.findByUserIdx(Long.parseLong(userIdx));

        List<Post> test = postRepository.findAllByUserAndEmotion(user, emotion);
        for(int i = 0; i < test.size(); i++){
            GetEmotionRes getEmotionRes = GetEmotionRes.builder()
                    .postIdx(test.get(i).getPostIdx())
                    .date(test.get(i).getDate())
                    .options(test.get(i).getOptions())
                    .build();
            result.add((getEmotionRes));
        }
        return result;
    }
}
