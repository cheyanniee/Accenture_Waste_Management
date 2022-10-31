package com.backend.controller;

import com.backend.request.ForgotPasswordRequest;
import com.backend.response.GeneralResponse;
import com.backend.service.ForgotPasswordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("dev/v1/forgotpassword")
public class ForgotPasswordController {

    @Autowired
    ForgotPasswordService forgotPasswordService;

    @GetMapping("listall")
    public ResponseEntity<?> listAll() {
        return ResponseEntity.ok(forgotPasswordService.listAllForgotPassword());
    }

    @PostMapping("sendotp")
    public ResponseEntity<?> sendOtp(@RequestBody ForgotPasswordRequest request) throws Exception {
        forgotPasswordService.createForgotPasswordModel(request);
        return ResponseEntity.ok(new GeneralResponse("OTP Send to your email."));
    }

//    @PostMapping("verifyotp")
//    public ResponseEntity<?> verifyOtp(@RequestBody ForgotPasswordRequest request) throws Exception {
//        forgotPasswordService.checkOtp(request);
//        return ResponseEntity.ok(new GeneralResponse("OTP verified!"));
//    }

    @PostMapping("reset")
    public ResponseEntity<?> resetPassword(@RequestBody ForgotPasswordRequest request) throws Exception {
        forgotPasswordService.resetPassword(request);
        return ResponseEntity.ok(new GeneralResponse("Password reset successfully!"));
    }
}
