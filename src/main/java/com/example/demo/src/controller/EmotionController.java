package com.example.demo.src.controller;

import com.example.demo.config.BaseResponse;
import com.example.demo.src.dto.response.BaseException;
import com.example.demo.src.dto.response.GetEmotionRes;
import com.example.demo.src.service.EmotionService;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(value = "/bring",produces = "application/json;charset=UTF-8")
public class EmotionController {

    private final EmotionService emotionService;

    public EmotionController(EmotionService emotionService) {
        this.emotionService = emotionService;
    }

    @ApiOperation("감정 조회하기")
    @GetMapping("/emotion")
    public BaseResponse<List<GetEmotionRes>> register(@RequestParam(value = "userIdx") String userIdx, @RequestParam(value = "emotion") String emotion) {
        try{
            List<GetEmotionRes> getEmotionRes = emotionService.search(userIdx, emotion);
            return new BaseResponse<>(getEmotionRes);
        } catch (BaseException e) {
            return new BaseResponse<>(e.getStatus());
        }
    }

}
