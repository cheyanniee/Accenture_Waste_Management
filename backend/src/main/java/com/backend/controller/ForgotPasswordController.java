package com.backend.controller;

import com.backend.request.ForgotPasswordRequest;
import com.backend.response.GeneralResponse;
import com.backend.service.ForgotPasswordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

//Purpose:
//    - Create URLs to send OTP and reset password when requested
//    - List all forgotPassword entries in forgot_password table of DB for testing purpose
//
//Restrictions:
//    - Nil. User do not need token to send OTP and reset password.
//    - Listall URL is not open to public, as it is for testing purpose. It needs token to access.
//
//Endpoints:
//      - /dev/v1/forgotpassword/listall
//      - /dev/v1/forgotpassword/sendotp
//      - /dev/v1/forgotpassword/reset
//
//Author:
//    - Liu Fang

@RestController
@RequestMapping("dev/v1/forgotpassword")
public class ForgotPasswordController {

    @Autowired
    ForgotPasswordService forgotPasswordService;

    @GetMapping("listall")
    public ResponseEntity<?> listAll() {
        return ResponseEntity.ok(forgotPasswordService.listAllForgotPassword());
    }

    @PostMapping("sendotp")//send otp to user email
    public ResponseEntity<?> sendOtp(@RequestBody ForgotPasswordRequest request) throws Exception {
        forgotPasswordService.createForgotPasswordModel(request);
        return ResponseEntity.ok(new GeneralResponse("OTP Send to your email."));
    }

//    @PostMapping("verifyotp")
//    public ResponseEntity<?> verifyOtp(@RequestBody ForgotPasswordRequest request) throws Exception {
//        forgotPasswordService.checkOtp(request);
//        return ResponseEntity.ok(new GeneralResponse("OTP verified!"));
//    }

    @PostMapping("reset")//reset password based on the new password requested
    public ResponseEntity<?> resetPassword(@RequestBody ForgotPasswordRequest request) throws Exception {
        forgotPasswordService.resetPassword(request);
        return ResponseEntity.ok(new GeneralResponse("Password reset successfully!"));
    }
}
