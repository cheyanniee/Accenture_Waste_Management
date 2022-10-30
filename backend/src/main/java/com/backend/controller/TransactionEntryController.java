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
    @PostMapping("create") // use for creating transaction entry
    public ResponseEntity<?> createTransactionEntry(@RequestBody TransactionEntryRequest transactionEntryRequest)  {
        try{
            TransactionEntryModel transactionEntryModel = transactionEntryService.createTransactionEntryWithRequest(transactionEntryRequest);
            return ResponseEntity.ok(transactionEntryModel);
        }catch (Exception e){
            return ResponseEntity.badRequest().body(new GeneralResponse(e.getMessage()));
        }
    }

    @PostMapping("delete/Entry") // use for creating transaction entry
    public ResponseEntity<?> deleteTransactionEntry(@RequestBody TransactionEntryRequest transactionEntryRequest)  {
        try{
            transactionEntryService.createTransactionEntryWithRequest(transactionEntryRequest);
            return ResponseEntity.ok(new GeneralResponse("Transaction entry created, please check."));
        }catch (Exception e){
            return ResponseEntity.badRequest().body(new GeneralResponse(e.getMessage()));
        }
    }

}
