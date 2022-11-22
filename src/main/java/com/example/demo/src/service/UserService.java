package com.example.demo.src.service;

import com.example.demo.config.secret.Secret;
import com.example.demo.src.dto.request.PostLoginReq;
import com.example.demo.src.dto.response.GetUserIdxRes;
import com.example.demo.src.dto.response.PostLoginRes;
import com.example.demo.src.repository.UserRepository;
import com.example.demo.src.utils.AES128;

import com.example.demo.src.dto.response.PostUserRes;
import com.example.demo.src.dto.request.PostUserReq;
import com.example.demo.src.dto.response.BaseException;
import com.example.demo.src.entity.User;
import lombok.Builder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;

import static com.example.demo.config.BaseResponseStatus.*;

@Service
public class UserService implements UserDetailsService {
    private final UserRepository userRepository;
    private final JwtService jwtService;


    @Autowired
    public UserService(UserRepository userRepository,JwtService jwtService){
        this.userRepository=userRepository;
        this.jwtService=jwtService;

    }

    public long saveUser(PostUserReq postUserDto) throws BaseException {

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


    public PostUserRes findUser(Long userIdx){

        User user=userRepository.findByUserIdx(userIdx);
        String jwt= jwtService.createJwt(userIdx);
        System.out.println(jwt);


        return PostUserRes.builder()
                .userIdx(user.getUserIdx())
                .kakao_id(user.getKakaoId())
                .kakao_nickname(user.getKakaoNickname())
                .profile_img_url(user.getProfileImgUrl())
                .gender(user.getGender())
                .jwt(jwt)
                .build();

    }

    @Builder
    public PostLoginRes loginUser(PostLoginReq postLoginReq)throws BaseException{



        User userByEmail=userRepository.findByKakaoEmail(postLoginReq.getEmail());

        // 유저가 존재하지 않는다는거 -> 로그인 에러
        if(userByEmail==null){
            throw new BaseException(NO_USER_EXISTS);
        }


        String jwt= jwtService.createJwt(userByEmail.getUserIdx());
        return PostLoginRes.builder()
                .userIdx(userByEmail.getUserIdx())
                .kakao_nickname(userByEmail.getKakaoNickname())
                .jwt(jwt)
                .build();

    }


    public GetUserIdxRes findUserIdx() throws BaseException{
        Long userIdx= jwtService.getUserIdx();
        return GetUserIdxRes.builder()
                .userIdx(userIdx)
                .build();

    }

    @Transactional(readOnly = true)
    @Override // 기본적인 반환 타입은 UserDetails, UserDetails를 상속받은 UserInfo로 반환 타입 지정 (자동으로 다운 캐스팅됨)
    public User loadUserByUsername(String email) throws UsernameNotFoundException { // 시큐리티에서 지정한 서비스이기 때문에 이 메소드를 필수로 구현
        try{
            return userRepository.findByKakaoEmail(email);
        }
        catch(Exception e){
            throw new UsernameNotFoundException("사용자를 찾을 수 없습니다.");
        }


    }
}
