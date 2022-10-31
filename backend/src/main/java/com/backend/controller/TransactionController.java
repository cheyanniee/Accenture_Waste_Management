package com.backend.controller;

import com.backend.model.PeopleModel;
import com.backend.model.TransactionModel;
import com.backend.request.ConfirmTransactionRequest;
import com.backend.request.TransactionRequest;
import com.backend.response.GeneralResponse;
import com.backend.service.PeopleService;
import com.backend.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/*
    Purpose:
        - APIs for TransactionModel-related operations
        - Allow People (Admin) to view all existing TransactionModel
        - Allow People or Machine to find specific TransactionModel by PeopleId or TransactionId
        - Create Transaction on vending machine for recycling or exchanging of batteries; able to confirm if Transaction
        is to proceed or to abort (to recycle/exchange or not)

    Restriction:
        - Only those with ROLES.Admin will be able to see all TransactionModels.

    Endpoints:
        - dev/v1/transaction/listall
        - dev/v1/transaction/find
        - dev/v1/transaction/findOne
        - dev/v1/transaction/find
        - dev/v1/transaction/update
        - dev/v1/transaction/create/start-r
        - dev/v1/transaction/create/start-e
        - dev/v1/transaction/create/yes
        - dev/v1/transaction/create/no

    Author:
        - Lew Xu Hong (all related classes i.e. Model, Repo, Service, Request, Controller, ConfirmTransactionRequest)
*/
@RestController
@RequestMapping("dev/v1/transaction")
public class TransactionController {
    @Autowired
    TransactionService transactionService;
    @Autowired
    PeopleService peopleService;

    @GetMapping("listall")
    public ResponseEntity<?> listTransaction(@RequestHeader String token) {
        try{
            PeopleModel peopleModel = peopleService.getPeopleById(peopleService.getIdByToken(token));
            if (!peopleModel.getRole().equals(PeopleModel.Role.admin)){
                return ResponseEntity.badRequest().body(new GeneralResponse("User does not have rights to acccess page"));
            }else{
                return ResponseEntity.ok(transactionService.listTransaction()); // keep this
            }
        }catch (Exception e){
            return ResponseEntity.badRequest().body(new GeneralResponse(e.getMessage()));
        }
    }

    //View Transaction by PeopleId
    @GetMapping("find")
    public ResponseEntity<?> listTransactionByPeopleId(@RequestHeader String token)  {
        try{
            PeopleModel peopleModel = peopleService.getPeopleById(peopleService.getIdByToken(token));
            return ResponseEntity.ok(transactionService.getTransactionByPeopleId(peopleModel));
        }catch (Exception e){
            return ResponseEntity.badRequest().body(new GeneralResponse(e.getMessage()));
        }
    }

    //View Transaction by TransactionId
    @GetMapping("findOne")
    public ResponseEntity<?> findTransaction(@RequestHeader String token, @RequestBody Long id)  {
        try{
            TransactionModel transactionModel = transactionService.getTransaction(id);
            return ResponseEntity.ok(transactionModel);
        }catch (Exception e){
            return ResponseEntity.badRequest().body(new GeneralResponse(e.getMessage()));
        }
    }

    //Create Transaction when user choose Recycle
    @PostMapping("create/start-r")
    public ResponseEntity<?> createTempTransactionR (@RequestHeader String token)  {
        PeopleModel.Role roleTemp = peopleService.getRoleByToken(token);
        if (roleTemp.equals(PeopleModel.Role.user) || roleTemp.equals(PeopleModel.Role.admin)){
            try{
                TransactionModel transactionModel = transactionService.createTempTransactionWithParams(token,
                        TransactionModel.Choose.recycle);
                return ResponseEntity.ok(transactionModel); //to get the id of the newly created TransactionModel
            }catch (Exception e){
                return ResponseEntity.badRequest().body(new GeneralResponse(e.getMessage()));
            }
        }else{
            return ResponseEntity.badRequest().body(new GeneralResponse("You do not have rights to access this page on the machine"));
        }
    }

    //Create Transaction when user choose Exchange
    @PostMapping("create/start-e")
    public ResponseEntity<?> createTempTransactionE (@RequestHeader String token)  {
        PeopleModel.Role roleTemp = peopleService.getRoleByToken(token);
        if (roleTemp.equals(PeopleModel.Role.user) || roleTemp.equals(PeopleModel.Role.admin)){
            try{
                TransactionModel transactionModel = transactionService.createTempTransactionWithParams(token,
                        TransactionModel.Choose.exchange);
                return ResponseEntity.ok(transactionModel); //to get the id of the newly created transactionModel
            }catch (Exception e){
                return ResponseEntity.badRequest().body(new GeneralResponse(e.getMessage()));
            }
        }else{
            return ResponseEntity.badRequest().body(new GeneralResponse("You do not have rights to acccess machine"));
        }
    }

    //Confirm keeping Transaction when PeopleModel clicks confirm, thereby updating balance (People) and storage (Machine)
    @PostMapping("create/yes")
    public ResponseEntity<?> confirmTransaction (@RequestBody ConfirmTransactionRequest confirmTransactionRequest,
                                                 @RequestHeader String token)  {
        try{
            Long transactionId = confirmTransactionRequest.getTransactionId();

            //craete a new TransactionRequest for the method to updateTransactionByYes
            TransactionRequest transactionRequest = TransactionRequest.builder()
                    .peopleId(confirmTransactionRequest.getPeopleId())
                    .machineId(confirmTransactionRequest.getMachineId())
                    .chooseType(confirmTransactionRequest.getChooseType())
                    .build();
            transactionService.updateTransactionByYes(transactionRequest, transactionId, token);
            return ResponseEntity.ok(new GeneralResponse("Transaction done, balance updated."));
        }catch (Exception e){
            return ResponseEntity.badRequest().body(new GeneralResponse(e.getMessage()));
        }
    }

    //Delete the Transaction and related TransactionEntry when person clicks cancel, no changes to balance or storage
    @PostMapping("create/no")
    public ResponseEntity<?> deleteTempTransaction (@RequestBody ConfirmTransactionRequest confirmTransactionRequest)  {
        try{
            transactionService.deleteTransactionByNo(confirmTransactionRequest.getTransactionId());
            return ResponseEntity.ok(new GeneralResponse("Transaction deleted, please start over."));
        }catch (Exception e){
            return ResponseEntity.badRequest().body(new GeneralResponse(e.getMessage()));
        }
    }
}
