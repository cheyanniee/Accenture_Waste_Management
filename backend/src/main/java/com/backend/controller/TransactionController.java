package com.backend.controller;

import com.backend.model.PeopleModel;
import com.backend.model.TransactionModel;
import com.backend.request.BalanceRequest;
import com.backend.request.ConfirmTransactionRequest;
import com.backend.request.TransactionEntryRequest;
import com.backend.request.TransactionRequest;
import com.backend.response.GeneralResponse;
import com.backend.service.BalanceService;
import com.backend.service.PeopleService;
import com.backend.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.ZonedDateTime;

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

    @GetMapping("find") // view transaction by peopleId
    public ResponseEntity<?> listTransactionByPeopleId(@RequestHeader String token)  {

        try{
            PeopleModel peopleModel = peopleService.getPeopleById(peopleService.getIdByToken(token));
            return ResponseEntity.ok(transactionService.getTransactionByPeopleId(peopleModel));
        }catch (Exception e){
            return ResponseEntity.badRequest().body(new GeneralResponse(e.getMessage()));
        }
    }

    @PostMapping("create/start-r") //create a transaction and eventually update balance (from Machine, but need another API)
    public ResponseEntity<?> createTempTransactionR (@RequestHeader String token)  {
        PeopleModel.Role roleTemp = peopleService.getRoleByToken(token);
        if (roleTemp.equals(PeopleModel.Role.user) || roleTemp.equals(PeopleModel.Role.admin)){
            try{
                TransactionModel transactionModel = transactionService.createTempTransactionWithReturn();
                transactionModel.setPeopleModel(peopleService.getPeopleById(peopleService.getIdByToken(token)));
                transactionModel.setChoose(TransactionModel.Choose.recycle);
                transactionService.saveChooseType(transactionModel);
                return ResponseEntity.ok(transactionModel); //to get the id of the newly created transactionModel
            }catch (Exception e){
                return ResponseEntity.badRequest().body(new GeneralResponse(e.getMessage()));
            }
        }else{
            return ResponseEntity.badRequest().body(new GeneralResponse("You do not have rights to acccess machine"));
        }
    }

    @PostMapping("create/start-e") //create a transaction and eventually update balance (from Machine, but need another API)
    public ResponseEntity<?> createTempTransactionE (@RequestHeader String token)  {
        PeopleModel.Role roleTemp = peopleService.getRoleByToken(token);
        if (roleTemp.equals(PeopleModel.Role.user) || roleTemp.equals(PeopleModel.Role.admin)){
            try{
                TransactionModel transactionModel = transactionService.createTempTransactionWithReturn();
                transactionModel.setPeopleModel(peopleService.getPeopleById(peopleService.getIdByToken(token)));
                transactionModel.setChoose(TransactionModel.Choose.exchange);
                transactionService.saveChooseType(transactionModel);
                return ResponseEntity.ok(transactionModel); //to get the id of the newly created transactionModel
            }catch (Exception e){
                return ResponseEntity.badRequest().body(new GeneralResponse(e.getMessage()));
            }
        }else{
            return ResponseEntity.badRequest().body(new GeneralResponse("You do not have rights to acccess machine"));
        }
    }

    @PostMapping("create/yes") //create a transaction and eventually update balance (from Machine, but need another API)
    public ResponseEntity<?> confirmTransaction (@RequestBody ConfirmTransactionRequest confirmTransactionRequest)  {
        try{

            Long transactionId = confirmTransactionRequest.getTransactionId();

            TransactionRequest transactionRequest = TransactionRequest.builder()
                    .peopleId(confirmTransactionRequest.getPeopleId())
                    .machineId(confirmTransactionRequest.getMachineId())
                    .chooseType(confirmTransactionRequest.getChooseType())
                    .build();
            transactionService.updateTransactionByYes(transactionRequest, transactionId);
            return ResponseEntity.ok(new GeneralResponse("Transaction done, balance updated."));
        }catch (Exception e){
            return ResponseEntity.badRequest().body(new GeneralResponse(e.getMessage()));
        }
    }

    @PostMapping("create/no") //create a transaction and eventually update balance (from Machine, but need another API)
    public ResponseEntity<?> deleteTempTransaction (@RequestBody ConfirmTransactionRequest confirmTransactionRequest)  {
        try{
            transactionService.deleteTransactionByNo(confirmTransactionRequest.getTransactionId());
            return ResponseEntity.ok(new GeneralResponse("Transaction deleted, please start over."));
        }catch (Exception e){
            return ResponseEntity.badRequest().body(new GeneralResponse(e.getMessage()));
        }
    }

    //should we allow people to manually update transaction and transaction entries?
//    @PostMapping("delete")
//    public ResponseEntity<?> deleteTransaction (@RequestBody Long transactionId)  {
//        try{
//            transactionService.deleteTransaction(transactionId);
//            return ResponseEntity.ok(new GeneralResponse("Transaction deleted."));
//        }catch (Exception e){
//            return ResponseEntity.badRequest().body(new GeneralResponse(e.getMessage()));
//        }
//    }

    // view transaction details by Id

}
