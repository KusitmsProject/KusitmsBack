package com.example.demo.src.controller;

import com.example.demo.src.dto.request.PostPostingReq;
import com.example.demo.src.dto.response.BaseException;
import com.example.demo.config.BaseResponse;
import com.example.demo.src.dto.response.PostPostingRes;
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

}
