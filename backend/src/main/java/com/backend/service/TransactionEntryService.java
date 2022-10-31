package com.backend.service;


import com.backend.configuration.CustomException;
import com.backend.model.*;
import com.backend.repo.BatteryRepo;
import com.backend.repo.RateRepo;
import com.backend.repo.TransactionEntryRepo;
import com.backend.repo.TransactionRepo;
import com.backend.request.TransactionEntryRequest;
import com.backend.request.TransactionRequest;
import com.backend.response.GeneralResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;

import java.util.List;
import java.util.Optional;

@Service
public class TransactionEntryService {

    @Autowired
    TransactionEntryRepo transactionEntryRepo;

    @Autowired
    TransactionService transactionService;

    @Autowired
    BatteryRepo batteryRepo;

    @Autowired
    RateRepo rateRepo;

    public List<TransactionEntryModel> listAllTransactionEntry() {return transactionEntryRepo.findAll();}

    //getting id by transactionModel
//    public Optional<List<TransactionEntryModel>> getTransactionEntryByTransactionId(TransactionModel transactionModel) throws CustomException {
//        return transactionEntryRepo.getTransactionEntryByTransactionId(transactionModel.getId());
//    }
    public Optional<List<TransactionEntryModel>> getTransactionEntryByTransactionId(Long transaction_id) throws CustomException {
        return transactionEntryRepo.getTransactionEntryByTransactionId(transaction_id);
    }

    //how to get the collected points from TransactionEntry and collect them into Transaction?
    //field needs to be changed on Transaction is balanceChange.
    //TransactionEntry should have a method to calculate the balanceChange and pass to the new Transaction
    //need to be able to create one row of TransactionEntry, then after confirm, call calculatePoints
    //calculatePoints will return the integer to Transaction create to be used.

    public TransactionEntryModel createTransactionEntryWithRequest(TransactionEntryRequest transactionEntryRequest) throws Exception {

        //by right the TransactionEntry request should have the transactionId.
        // if it doesn't exist we create on the spot and should be that id
        // so we can still use TransactionEntry request's id to get the Transaction

   //     TransactionModel transactionModel = transactionService.checkTransactionIsExist(transactionEntryRequest);
        //find transaction first, if not create one then get by its the current id.

        TransactionModel transactionModel = transactionService.getTransaction(transactionEntryRequest.getTransactionId());

        TransactionEntryModel transactionEntryNew = TransactionEntryModel.builder()
                .transactionModel(transactionModel)
                //.transactionModel(transactionModel)
                //.batteryModel(batteryRepo.getBatteryByType(transactionEntryRequest.getBatteryType()).orElseThrow(() -> new CustomException("No such battery type exist")))
               // .rateModel(rateRepo.getRateByCategory(transactionEntryRequest.getBatteryType()).orElseThrow(() -> new CustomException("No such rate type exist")))
                .quantity(transactionEntryRequest.getQuantity())
                .build();

        String type = transactionEntryRequest.getBatteryType();

        //determine which table to compare by using Choose - exchange or recycle
        if (transactionModel.getChoose().equals(TransactionModel.Choose.recycle)){
            transactionEntryNew.setBatteryModel(batteryRepo.getBatteryByType(type).orElseThrow(() -> new CustomException("No such battery type exist")));
        }
        if (transactionModel.getChoose().equals(TransactionModel.Choose.exchange)){
            transactionEntryNew.setRateModel(rateRepo.getRateByType(type).orElseThrow(() -> new CustomException("No such rate type exist")));
        }

        transactionEntryRepo.save(transactionEntryNew);

        //return id of the Transaction it is tied to.

        return transactionEntryNew;
    }


    public void deleteTransactionEntry(Long id){
        TransactionEntryModel transactionEntryModel = transactionEntryRepo.getById(id);
        transactionEntryRepo.delete(transactionEntryModel);
    }

    //manual update method
    public void updateTransactionEntry(TransactionEntryRequest transactionEntryRequest, Long id) throws Exception{
        TransactionEntryModel transactionEntryModel = transactionEntryRepo.findById(id).orElseThrow(() -> new CustomException("Transaction Entry not found!"));//get the data bases on primary key

        if (transactionEntryRequest.getTransactionId() != null && !transactionEntryRequest.getTransactionId().equals("")) {
            transactionEntryModel.setTransactionModel(transactionService.getTransaction(transactionEntryRequest.getTransactionId()));
        }
        if (transactionEntryRequest.getBatteryType() != null && !transactionEntryRequest.getBatteryType().equals("")) {
            transactionEntryModel.setBatteryModel(transactionEntryModel.getBatteryModel());
        }
        if (transactionEntryRequest.getQuantity() != null) {
            transactionEntryModel.setQuantity(transactionEntryModel.getQuantity());
        }
        transactionEntryRepo.save(transactionEntryModel);
    }


}
