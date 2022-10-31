package com.backend.service;


import com.backend.configuration.CustomException;
import com.backend.model.*;
import com.backend.repo.BatteryRepo;
import com.backend.repo.RateRepo;
import com.backend.repo.TransactionEntryRepo;
import com.backend.request.TransactionEntryRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


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

    public Optional<List<TransactionEntryModel>> getTransactionEntryByTransactionId(Long transaction_id) throws CustomException {
        return transactionEntryRepo.getTransactionEntryByTransactionId(transaction_id);
    }


    //Create a TransactionEntryModel linked to the Transaction just created, for vending machine
    public TransactionEntryModel createTransactionEntryWithRequest(TransactionEntryRequest transactionEntryRequest) throws Exception {
        //find the newly created TransactionModel in order to continue creating TransactionEntryModel
        TransactionModel transactionModel = transactionService.getTransaction(transactionEntryRequest.getTransactionId());

        TransactionEntryModel transactionEntryNew = TransactionEntryModel.builder()
                .transactionModel(transactionModel)
                .batteryModel(batteryRepo.getBatteryByType(transactionEntryRequest.getBatteryType()).orElseThrow(() -> new CustomException("No such battery type exist")))
                .quantity(transactionEntryRequest.getQuantity())
                .build();

        transactionEntryRepo.save(transactionEntryNew);

        //return the new TransactionEntryModel with its id
        return transactionEntryNew;
    }

    public void deleteTransactionEntry(Long id){
        TransactionEntryModel transactionEntryModel = transactionEntryRepo.getById(id);
        transactionEntryRepo.delete(transactionEntryModel);
    }

    //method to allow manual update of a TransactionEntryModel
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
