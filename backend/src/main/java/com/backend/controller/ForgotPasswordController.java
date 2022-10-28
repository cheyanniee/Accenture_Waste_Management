package com.backend.controller;

import com.backend.service.ForgotPasswordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("dev/v1/forgotpassword")
public class ForgotPasswordController {

    @Autowired
    ForgotPasswordService forgotPasswordService;

    @GetMapping("listall")
    public ResponseEntity<?> listAll() {
        return ResponseEntity.ok(forgotPasswordService.listAllForgotPassword());
    }
}
