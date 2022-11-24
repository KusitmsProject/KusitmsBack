package com.example.demo.src.controller;

import com.example.demo.config.BaseResponse;
import com.example.demo.src.dto.response.BaseException;
import com.example.demo.src.dto.response.GetSearchEmotionRes;
import com.example.demo.src.dto.response.GetSearchTrackRes;
import com.example.demo.src.service.EmotionService;
import com.example.demo.src.service.JwtService;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping(value = "/bring/emotion",produces = "application/json;charset=UTF-8")
public class EmotionController {

    private final EmotionService emotionService;
    private final JwtService jwtService;

    public EmotionController(EmotionService emotionService, JwtService jwtService) {

        this.emotionService = emotionService;
        this.jwtService = jwtService;
    }

    @ApiOperation("해당 감정 목록 조회하기")
    @GetMapping("/searchEmotion")
    public BaseResponse<List<GetSearchEmotionRes>> searchEmotion(@RequestParam(value = "emotion") String emotion) {
        try{
            Long userIdx=jwtService.getUserIdx();
            List<GetSearchEmotionRes> getSearchEmotionRes = emotionService.searchForEmotion(userIdx, emotion);
            return new BaseResponse<>(getSearchEmotionRes);
        } catch (BaseException e) {
            return new BaseResponse<>(e.getStatus());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @ApiOperation("감정목록 랜덤 반환")
    @GetMapping("/randomEmotion")
    public BaseResponse<List<GetSearchEmotionRes>> randomEmotion() {
        try{
            Long userIdx=jwtService.getUserIdx();
            List<GetSearchEmotionRes> getSearchEmotionRes = emotionService.searchRandomEmotion(userIdx);
            return new BaseResponse<>(getSearchEmotionRes);
        } catch (BaseException e) {
            return new BaseResponse<>(e.getStatus());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @ApiOperation("해당 노래 목록 조회하기")
    @GetMapping("/searchTrack")
    public BaseResponse<List<List<GetSearchTrackRes>>> searchTrack(@RequestParam(value = "musicIdx") String musicIdx) {
        try{
            Long userIdx=jwtService.getUserIdx();
            List<List<GetSearchTrackRes>> getSearchTrackRes = emotionService.searchForTrack(userIdx, musicIdx);
            return new BaseResponse<>(getSearchTrackRes);
        } catch (BaseException e) {
            return new BaseResponse<>(e.getStatus());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @ApiOperation("감정보관함 노래 검색")
    @GetMapping("/search")
    public BaseResponse<List<GetSearchTrackRes>> search(@RequestParam(value = "input") String input) {
        try{
            Long userIdx=jwtService.getUserIdx();
            List<GetSearchTrackRes> getSearchTrackRes = emotionService.search(userIdx, input);
            return new BaseResponse<>(getSearchTrackRes);
        } catch (BaseException e) {
            return new BaseResponse<>(e.getStatus());
        }
    }

}
