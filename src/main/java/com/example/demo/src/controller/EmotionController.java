package com.example.demo.src.controller;

import com.example.demo.config.BaseResponse;
import com.example.demo.src.dto.response.BaseException;
import com.example.demo.src.dto.response.GetSearchEmotionRes;
import com.example.demo.src.dto.response.GetSearchTrackRes;
import com.example.demo.src.service.EmotionService;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/bring/emotion",produces = "application/json;charset=UTF-8")
public class EmotionController {

    private final EmotionService emotionService;

    public EmotionController(EmotionService emotionService) {
        this.emotionService = emotionService;
    }

    @ApiOperation("해당 감정 목록 조회하기")
    @GetMapping("/searchEmotion")
    public BaseResponse<List<GetSearchEmotionRes>> searchEmotion(@RequestParam(value = "userIdx") String userIdx, @RequestParam(value = "emotion") String emotion) {
        try{
            List<GetSearchEmotionRes> getSearchEmotionRes = emotionService.searchForEmotion(userIdx, emotion);
            return new BaseResponse<>(getSearchEmotionRes);
        } catch (BaseException e) {
            return new BaseResponse<>(e.getStatus());
        }
    }

    @ApiOperation("해당 노래 목록 조회하기")
    @GetMapping("/searchTrack")
    public BaseResponse<List<GetSearchTrackRes>> searchTrack(@RequestParam(value = "userIdx") String userIdx, @RequestParam(value = "musicIdx") String musicIdx) {
        try{
            List<GetSearchTrackRes> getSearchTrackRes = emotionService.searchForTrack(userIdx, musicIdx);
            return new BaseResponse<>(getSearchTrackRes);
        } catch (BaseException e) {
            return new BaseResponse<>(e.getStatus());
        }
    }

}
