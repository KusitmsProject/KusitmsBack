package com.example.demo.src.service;

import com.example.demo.src.dto.request.PostLoginReq;
import com.example.demo.src.dto.response.*;
import com.example.demo.src.entity.Post;
import com.example.demo.src.repository.CalendarRepository;
import com.example.demo.src.repository.UserRepository;

import com.example.demo.src.dto.request.PostUserReq;
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

    private final CalendarRepository calendarRepository;




    @Autowired
    public UserService(UserRepository userRepository, JwtService jwtService, CalendarRepository calendarRepository){
        this.userRepository=userRepository;
        this.jwtService=jwtService;


        this.calendarRepository = calendarRepository;
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

                .kakao_nickname(user.getKakaoNickname())

                .jwt(jwt)
                .build();

    }

    @Builder
    public PostLoginRes loginUser(PostLoginReq postLoginReq)throws BaseException{



        User userByEmail=userRepository.findByKakaoEmail(postLoginReq.getEmail());

        // 유저가 존재하지 않는다는거 -> 유저 저장하기
        if(userByEmail==null){
            userByEmail=User.builder()
                    .kakaoEmail(postLoginReq.getEmail())
                    .kakaoNickname(postLoginReq.getKakao_nickname())
                    .build();

            userRepository.save(userByEmail);



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
        User user=userRepository.findByUserIdx(userIdx);
        return GetUserIdxRes.builder()
                .userIdx(userIdx)
                .kakao_nickname(user.getKakaoNickname())
                .build();

    }

    //작년의 오늘

    public GetLastYearRes findLastYear(String year,String month,String day,Long userIdx)throws BaseException{

        year=Integer.toString(Integer.parseInt(year)-1);
        String startTime=year.concat("-").concat(month).concat("-").concat(day);
        String endTime=year.concat("-").concat(month).concat("-").concat(day);

        String date=year.concat(".").concat(month).concat(".").concat(day);
        Post post=calendarRepository.findByMonthYearDay(startTime,endTime,1,userIdx);

        System.out.println(post.getMusic().getTrack());
        return GetLastYearRes.builder()
                .artist(post.getMusic().getArtist())
                .track(post.getMusic().getTrack())
                .friendList(post.getFriendList())
                .date(date)
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
