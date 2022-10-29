package com.backend.controller;

import com.backend.service.BalanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("dev/v1/balance")
public class BalanceController {

    @Autowired
    BalanceService balanceService;

    @GetMapping("listall")
    public ResponseEntity<?> listBalance() {
        return ResponseEntity.ok(balanceService.listBalance());
    }
}
