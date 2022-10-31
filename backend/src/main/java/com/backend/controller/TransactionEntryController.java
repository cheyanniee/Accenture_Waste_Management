package com.backend.controller;

import com.backend.model.PeopleModel;
import com.backend.model.TransactionEntryModel;
import com.backend.model.TransactionModel;
import com.backend.request.TransactionEntryRequest;
import com.backend.request.TransactionRequest;
import com.backend.response.GeneralResponse;
import com.backend.service.PeopleService;
import com.backend.service.TransactionEntryService;
import com.backend.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


/*
    Purpose:
        - APIs for TransactionEntryModel-related operations
        - Allow People (Admin) to view all existing TransactionEntryModel
        - Allow People or Machine to find all TransactionEntryModels of a specific TransactionModel
        - Create TransactionEntry on vending machine for recycling or exchanging of batteries;

    Restriction:
        - Only those with ROLES.Admin will be able to see all TransactionModels.

    Endpoints:
        - dev/v1/transactionentry/listall
        - dev/v1/transactionentry/find
        - dev/v1/transactionentry/create

    Author:
        - Lew Xu Hong (all related classes i.e. Model, Repo, Service, Request, Controller)
*/

@RestController
@RequestMapping("dev/v1/transactionentry")
public class TransactionEntryController {

    @Autowired
    TransactionEntryService transactionEntryService;
    @Autowired
    PeopleService peopleService;

    @Autowired
    TransactionService transactionService;


    @GetMapping("listall")
    public ResponseEntity<?> listTransactionEntry(@RequestHeader String token) {
        try{
            PeopleModel peopleModel = peopleService.getPeopleById(peopleService.getIdByToken(token));
            if (!peopleModel.getRole().equals(PeopleModel.Role.admin)){
                return ResponseEntity.badRequest().body(new GeneralResponse("User does not have rights to acccess page"));
            }else{
                return ResponseEntity.ok(transactionEntryService.listAllTransactionEntry()); // keep this
            }
        }catch (Exception e){
            return ResponseEntity.badRequest().body(new GeneralResponse(e.getMessage()));
        }
    }

    @PostMapping("find")
    public ResponseEntity<?> listTransactionEntryByTransactionId(@RequestHeader String token,
                                                                 @RequestBody TransactionEntryRequest transactionEntryRequest)  {
        try{
            Long id = transactionEntryRequest.getTransactionId();
            return ResponseEntity.ok(transactionEntryService.getTransactionEntryByTransactionId(id));
        }catch (Exception e){
            return ResponseEntity.badRequest().body(new GeneralResponse(e.getMessage()));
        }
    }

    //Create a TransactionEntry, used in conjunction with vending machine flow
    @PostMapping("create")
    public ResponseEntity<?> createTransactionEntry(@RequestBody TransactionEntryRequest transactionEntryRequest)  {
        try{
            TransactionEntryModel transactionEntryModel = transactionEntryService.createTransactionEntryWithRequest(transactionEntryRequest);
            return ResponseEntity.ok(transactionEntryModel);
        }catch (Exception e){
            return ResponseEntity.badRequest().body(new GeneralResponse(e.getMessage()));
        }
    }
}
