package com.backend.controller;

import com.backend.service.BalanceService;
import com.backend.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("dev/v1/transaction")
public class TransactionController {
    @Autowired
    TransactionService transactionService;

    @GetMapping("listall")
    public ResponseEntity<?> listTransaction() {
        return ResponseEntity.ok(transactionService.listTransaction());
    }

    //link to balance

    //machine must be able to add to transaction

    //view transaction details by Id



}
