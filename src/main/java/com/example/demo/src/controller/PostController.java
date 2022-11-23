package com.example.demo.src.controller;

import com.example.demo.src.dto.request.PostMomentPostingReq;
import com.example.demo.src.dto.request.PostPostingReq;
import com.example.demo.src.dto.request.PostTodayPostingReq;
import com.example.demo.src.dto.response.BaseException;
import com.example.demo.config.BaseResponse;
import com.example.demo.src.dto.response.PostPostingRes;
import com.example.demo.src.dto.response.PostTodayPostingRes;
import com.example.demo.src.service.PostService;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/bring")
public class PostController {

    private final PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    @ApiOperation("게시물 등록")
    @PostMapping("/post")
    public BaseResponse<PostPostingRes> register(@Valid @RequestBody PostPostingReq postPostingReq) {
        try{
            PostPostingRes postPostingRes = postService.register(postPostingReq);
            return new BaseResponse<>(postPostingRes);
        } catch (BaseException e) {
            return new BaseResponse<>(e.getStatus());
        }
    }

    //오늘의 나 게시물 등록 ->PostTodayPostingReq

    @PostMapping("/post/today")
    public BaseResponse<PostTodayPostingRes> postToday(@RequestBody PostTodayPostingReq postTodayPostingReq){
        try{
            PostTodayPostingRes postTodayPostingRes=postService.postToday(postTodayPostingReq);
            return new BaseResponse<>(postTodayPostingRes);

        }
        catch (BaseException e) {
            return new BaseResponse<>(e.getStatus());
        }
    }



    //그때의 나 게시물 등록 ->PostPostingReq
    @PostMapping("/post/moment")
    public BaseResponse<PostPostingRes> postToday(@RequestBody PostMomentPostingReq postMomentPostingReq){
        try{
            PostPostingRes postPostingRes=postService.postMoment(postMomentPostingReq);
            return new BaseResponse<>(postPostingRes);

        }
        catch (BaseException e) {
            return new BaseResponse<>(e.getStatus());
        }
    }

}
