package com.example.demo.src.service;

import com.example.demo.config.secret.Secret;
import com.example.demo.src.repository.UserRepository;
import com.example.demo.src.utils.AES128;

import com.example.demo.src.dto.GetUserDto;
import com.example.demo.src.dto.PostUserDto;
import com.example.demo.src.dto.response.BaseException;
import com.example.demo.src.entity.User;
import lombok.Builder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;

import static com.example.demo.config.BaseResponseStatus.PASSWORD_ENCRYPTION_ERROR;

@Service
public class UserService implements UserDetailsService {
    private final UserRepository userRepository;
    private final JwtService jwtService;


    @Autowired
    public UserService(UserRepository userRepository,JwtService jwtService){
        this.userRepository=userRepository;
        this.jwtService=jwtService;

    }

    public long saveUser(PostUserDto postUserDto) throws BaseException {

        User user = userRepository.findByKakaoEmail(postUserDto.getKakao_email());

        if (user == null) {
            String pwd;
            try {
                // 암호화: postUserReq에서 제공받은 비밀번호를 보안을 위해 암호화시켜 DB에 저장
                // ex) password123 -> dfhsjfkjdsnj4@!$!@chdsnjfwkenjfnsjfnjsd.fdsfaifsadjfjaf
                pwd = new AES128(Secret.USER_INFO_PASSWORD_KEY).encrypt(postUserDto.getPassword()); // 암호화코드

            } catch (Exception ignored) { // 암호화가 실패하였을 경우 에러 발생
                throw new BaseException(PASSWORD_ENCRYPTION_ERROR);
            }
            user = (User.builder()
                    .kakaoId(postUserDto.getKakao_id())
                    .password(pwd)
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
        String jwt= jwtService.createJwt(userIdx);
        System.out.println(jwt);


        return GetUserDto.builder()
                .userIdx(user.getUserIdx())
                .kakao_id(user.getKakaoId())
                .kakao_nickname(user.getKakaoNickname())
                .profile_img_url(user.getProfileImgUrl())
                .gender(user.getGender())
                .jwt(jwt)
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
