package com.example.demo.src.controller;

import com.example.demo.config.BaseResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class ExceptionController {

    // 400
    @ExceptionHandler({

            RuntimeException.class
    })
    public BaseResponse<ResponseEntity<Object>> BadRequestException(final RuntimeException ex) {
        log.warn("error", ex);
        return new BaseResponse<>(ResponseEntity.badRequest().body(ex.getMessage()));
    }

    // 401
    @ExceptionHandler({ AccessDeniedException.class })
    public BaseResponse handleAccessDeniedException(final AccessDeniedException ex) {
        log.warn("error", ex);
        return new BaseResponse(ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(ex.getMessage()));
    }



    // 500
    @ExceptionHandler({ Exception.class })
    public BaseResponse<ResponseEntity<Object>> handleAll(final Exception ex) {
        log.info(ex.getClass().getName());
        log.error("error", ex);
        ResponseEntity<Object>responseEntity=new ResponseEntity<>(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        return new BaseResponse<>(responseEntity);
    }

}