package com.example.demo.src.controller;

import com.example.demo.src.dto.GetUserDto;
import com.example.demo.src.dto.response.BaseResponse;
import com.example.demo.src.service.KakaoService;
import com.example.demo.src.service.UserService;
import com.example.demo.src.service.KakaoService;
import com.example.demo.src.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;

@RestController
@RequestMapping("/bring")
public class KakaoController {

    @Autowired
    private KakaoService kakaoService;

    @Autowired
    private UserService userService;

    @Operation(summary = "카카오 로그인(테스트)", description = "/bring/login?code={인가코드}")
    @GetMapping(value="/kakaoLogin")
    public BaseResponse<GetUserDto> login(@RequestParam("code") String code) {
        String access_Token = kakaoService.getAccessToken(code);
        System.out.println("controller access_token : " + access_Token);
        HashMap<String, Object> userInfo = kakaoService.getUserInfo(access_Token);
        System.out.println("login Controller : " + userInfo);
        Long id=userService.saveKakaoUser(userInfo);
        GetUserDto getUserDto=userService.findUser(id);

        return new BaseResponse<>(getUserDto);

    }


}
