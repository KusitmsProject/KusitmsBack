package com.example.demo.src.service;

import com.example.demo.src.dto.GetUserDto;
import com.example.demo.src.dto.PostUserDto;
import com.example.demo.src.entity.User;
import lombok.Builder;
import org.springframework.stereotype.Service;

import java.util.HashMap;

@Service
public class UserService {
    private final com.example.demo.repository.UserRepository userRepository;

    public UserService(com.example.demo.repository.UserRepository userRepository){
        this.userRepository=userRepository;
    }

    public long saveUser(PostUserDto postUserDto) {

        User user = userRepository.findByKakaoEmail(postUserDto.getKakao_email());
        if (user == null) {
            user = (User.builder()
                    .kakaoId(postUserDto.getKakao_id())
                    .kakaoEmail(postUserDto.getKakao_email())
                    .kakaoNickname(postUserDto.getKakao_nickname())
                    .profileImgUrl(postUserDto.getProfile_img_url())
                    .gender(postUserDto.getGender())

                    .build());

            userRepository.save(user);


        }
        return userRepository.findByKakaoEmail(postUserDto.getKakao_email()).getUserIdx();
    }

    public long saveKakaoUser(HashMap<String, Object> userInfo){


        User user= userRepository.findByKakaoEmail(userInfo.get("kakaoEmail").toString());


        if(user==null){
            user= (User.builder()
                    .kakaoId((Long) userInfo.get("kakaoId"))
                    .kakaoEmail(userInfo.get("kakaoEmail").toString())
                    .kakaoNickname(userInfo.get("kakaoNickname").toString())
                    .profileImgUrl(userInfo.get("profileImgUrl").toString())
                    .gender(userInfo.get("gender").toString())

                    .build());

            userRepository.save(user);


        }

        return userRepository.findByKakaoEmail(userInfo.get("kakaoEmail").toString()).getUserIdx();


    }

    @Builder
    public GetUserDto findUser(Long userIdx){

        User user=userRepository.findByUserIdx(userIdx);

        return GetUserDto.builder()
                .userIdx(user.getUserIdx())
                .kakao_id(user.getUserIdx())
                .kakao_nickname(user.getKakaoNickname())
                .profile_img_url(user.getProfileImgUrl())
                .gender(user.getGender())
                .build();

    }
}
