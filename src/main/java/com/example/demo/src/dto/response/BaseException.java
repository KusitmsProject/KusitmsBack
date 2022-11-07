package com.example.demo.src.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class BaseException extends Exception {
    private com.example.demo.dto.response.BaseResponseStatus status;  //BaseResoinseStatus 객체에 매핑
}