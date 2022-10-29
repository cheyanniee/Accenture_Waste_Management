package com.backend.controller;

import com.backend.service.TransactionEntryService;
import com.backend.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("dev/v1/transactionentry")
public class TransactionEntryController {

    @Autowired
    TransactionEntryService transactionEntryService;

    @GetMapping("listall")
    public ResponseEntity<?> listTransactionEntry() {
        return ResponseEntity.ok(transactionEntryService.listTransaction());
    }

    //must be able to get transaction

    //list all transaction by id then pass back

}
