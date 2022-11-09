package com.example.demo.src.controller;

import com.example.demo.src.dto.GetUserDto;
import com.example.demo.src.dto.PostUserDto;
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
    @Operation(summary = "로그인(진짜)", description = "/bring/login")
    @PostMapping(value="/login")
    public BaseResponse<GetUserDto> login(@RequestBody PostUserDto postUserDto) throws BaseException {

        Long id=userService.saveUser(postUserDto);
        System.out.println(id);
        GetUserDto getUserDto=userService.findUser(id);

        return new BaseResponse<>(getUserDto);

    }
}
