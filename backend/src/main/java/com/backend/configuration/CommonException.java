package com.backend.configuration;

import com.backend.response.GeneralResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

//Purpose:
//    - Allow application to manage all exceptions in a central place
//
//Author:
//    - Liu Fang


@ControllerAdvice
public class CommonException {
    @ExceptionHandler(CustomException.class)
    public ResponseEntity<?> handleException(CustomException e) {
        return ResponseEntity.badRequest().body(new GeneralResponse("Custom Exception-- " + e.getMessage()));
    }
    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleException(Exception e) {
        return ResponseEntity.badRequest().body(new GeneralResponse(e.getMessage()));
    }
}
