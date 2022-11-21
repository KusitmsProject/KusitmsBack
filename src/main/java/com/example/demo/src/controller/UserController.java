package com.example.demo.src.controller;

import com.example.demo.src.dto.request.PostLoginReq;
import com.example.demo.src.dto.response.GetUserIdxRes;
import com.example.demo.src.dto.response.PostLoginRes;
import com.example.demo.src.dto.response.PostUserRes;
import com.example.demo.src.dto.request.PostUserReq;
import com.example.demo.src.dto.response.BaseException;
import com.example.demo.config.BaseResponse;
import com.example.demo.src.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/bring")
public class UserController {

    @Autowired
    UserService userService;

    // 회원가입 
    @Operation(summary = "회원가입", description = "/bring/signup")
    @PostMapping(value="/signup")
    public BaseResponse<PostUserRes> signUp(@RequestBody PostUserReq postUserDto) throws BaseException {

        try{
            Long id=userService.saveUser(postUserDto);
            System.out.println(id);
            PostUserRes getUserDto=userService.findUser(id);

            return new BaseResponse<>(getUserDto);
        }catch(BaseException e){
            return new BaseResponse<>(e.getStatus());
        }

    }

    // 로그인
    @PostMapping(value="login")
    public BaseResponse<PostLoginRes>login(@RequestBody PostLoginReq postLoginReq)throws BaseException{

        try{
            PostLoginRes postLoginRes=userService.loginUser(postLoginReq);

            return new BaseResponse<>(postLoginRes);
        }catch(BaseException e){
            return new BaseResponse<>(e.getStatus());
        }

    }

    //jwt로 유저 인덱스 찾기
    @GetMapping(value="userIdx")
    public BaseResponse<GetUserIdxRes> findUserIdx() throws BaseException{
        try{

            GetUserIdxRes getUserIdxRes=userService.findUserIdx();
            return  new BaseResponse<>(getUserIdxRes);
        }catch (BaseException e){
            return new BaseResponse<>(e.getStatus());
        }
    }
}
