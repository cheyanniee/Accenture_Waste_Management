package com.backend.controller;

import com.backend.model.PeopleModel;
import com.backend.request.BalanceRequest;
import com.backend.request.TransactionRequest;
import com.backend.response.GeneralResponse;
import com.backend.service.BalanceService;
import com.backend.service.PeopleService;
import com.backend.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("dev/v1/transaction")
public class TransactionController {
    @Autowired
    TransactionService transactionService;

    @Autowired
    PeopleService peopleService;

    @GetMapping("listall")
    public ResponseEntity<?> listTransaction() {
        return ResponseEntity.ok(transactionService.listTransaction());
    }

    @GetMapping("find")
    public ResponseEntity<?> listTransactionByPeopleId(@RequestHeader String token)  {

        try{
            PeopleModel peopleModel = peopleService.getPeopleById(peopleService.getIdByToken(token));
            return ResponseEntity.ok(transactionService.getTransactionByPeopleId(peopleModel));
        }catch (Exception e){
            return ResponseEntity.badRequest().body(new GeneralResponse(e.getMessage()));
        }
    }


    //link to balance

    //machine must be able to add to transaction

    //view transaction details by Id



}
