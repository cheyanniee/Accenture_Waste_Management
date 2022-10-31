package com.backend.service;

import com.backend.configuration.CustomException;
import com.backend.model.*;
import com.backend.repo.TransactionEntryRepo;
import com.backend.repo.TransactionRepo;
import com.backend.request.TransactionEntryRequest;
import com.backend.request.TransactionRequest;
import com.backend.response.GeneralResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;

/*
    Purpose:
        - Service methods to be used in Transaction-related APIs or assist in other functions
        - Major functions: creating a transaciton when confirmed, then update the related changes in balance and storage,
        deleting a transaction when cancelled

    Author:
        - Lew Xu Hong
*/

@Service
public class TransactionService {

    @Autowired
    TransactionRepo transactionRepo;

    @Autowired
    BalanceService balanceService;

    @Autowired
    PeopleService peopleService;

    @Autowired
    MachineService machineService;

    @Autowired
    TransactionEntryRepo transactionEntryRepo;

    @Autowired
    StorageService storageService;


    public List<TransactionModel> listTransaction() {
        return transactionRepo.findAll();
    }

    public TransactionModel getTransaction(Long id) throws Exception{
        return transactionRepo.findById(id).orElseThrow(() -> new Exception("Transaction not found"));}

    public Optional<List<TransactionModel>> getTransactionByPeopleId(PeopleModel peopleModel) throws CustomException {
        return transactionRepo.getTransactionByPeopleId(peopleModel.getId());
    }

    //Create the TransactionModel through vending machine flow i.e. recycle or exchange
    public TransactionModel createTempTransactionWithParams (String token, TransactionModel.Choose choose) throws Exception{
        TransactionModel transactionModel = TransactionModel.builder()
                .peopleModel(peopleService.getPeopleById(peopleService.getIdByToken(token)))
                .choose(choose)
                .balanceChange(0F)
                .build();
        transactionRepo.save(transactionModel);
        return transactionModel;
    }

    public void saveChooseType(TransactionModel transactionModel){
        transactionRepo.save(transactionModel);
    }


    //Update all details of the TransactionModel created by vending machine when person click yes
    //Updates the balance of the person from either recycle or exchange, and update storage quantity if exchanged
    public ResponseEntity<?> updateTransactionByYes (TransactionRequest transactionRequest, Long id, String token) throws Exception {
        //get the current TransactionModel
        TransactionModel transactionModel = getTransaction(id);

        //get the other required details
        ZoneId zid = ZoneId.of("Asia/Singapore");
        PeopleModel peopleModel = peopleService.getPeopleById(peopleService.getIdByToken(token));
        MachineModel machineModel = machineService.getMachineById(transactionRequest.getMachineId());
        //get all the TransactionEntryModels linked to the Transaction
        List<TransactionEntryModel> transactionEntryModelList =
                transactionEntryRepo.getTransactionEntryByTransactionId(id)
                        .orElseThrow(()-> new CustomException("Unable to find list of transactionEntries as no Transaction exists."));

        //declaring variables to start calculations for balance changes of recycle/exchange
        Float balanceChange = 0F;

        //battery type from BatteryModel is required for points calculation
        //exchangePointsRate only relevant when operation is exchange
        String batType = "";
        Float exchangePointsRate = 0F; //to use for calculating qty of batteries exchanged later

        //balanceChange calculated by adding all quantity*type of all TransactionEntryModels in the transaction
        //
        for (TransactionEntryModel teM: transactionEntryModelList) {
            if(transactionModel.getChoose().equals(TransactionModel.Choose.exchange)){
                batType = teM.getBatteryModel().getType();
                exchangePointsRate = teM.getBatteryModel().getExchangePoint();
                Float exchangeOne = teM.getBatteryModel().getExchangePoint() * teM.getQuantity();
                balanceChange += exchangeOne;
            }
            if (transactionModel.getChoose().equals(TransactionModel.Choose.recycle)){
                Float pointsOne = teM.getBatteryModel().getRecyclePoint() * teM.getQuantity();
                balanceChange += pointsOne;
            }
        }

        //checking number of batteries exchangeable by the points when it is exchange
        Integer batteriesExchanged = 0;
        if(transactionModel.getChoose().equals(TransactionModel.Choose.exchange)){
            batteriesExchanged = Math.round(balanceChange/exchangePointsRate);
        }

        //getting the number of batteries of the desired type in the current MachineModel's storage
        //only two types of batteries available for exchange - AA or AAA
        StorageModel storageModel = storageService.getStorageByMachineId(machineModel);
        Integer batteriesStored = 0;
        if (batType.equals("AAA")){
            batteriesStored = storageModel.getQtyAAA();
        }
        else if (batType.equals("AA")){
            batteriesStored = storageModel.getQtyAAA();
        }

        //if batteries not enough in storage for current machine, throw error and stop transaction
        if(batteriesStored < batteriesExchanged){
            throw new CustomException("Not enough batteries of this type in storage. Please start over");
        }

        //when everything is checked out (recycle or exchange), update all details into Transaction and save
        transactionModel.setPeopleModel(peopleModel);
        transactionModel.setMachineModel(machineModel);
        transactionModel.setBalanceChange(balanceChange);
        transactionModel.setChoose(transactionRequest.getChooseType());
        saveChooseType(transactionModel);
        transactionModel.setDateAndTime(ZonedDateTime.now(zid));
        transactionRepo.save(transactionModel);

        //creating a new TransactionRequest to update the balance and storage
        TransactionRequest transUpdate = TransactionRequest.builder()
                .peopleId(transactionModel.getPeopleModel().getId())
                .chooseType(transactionRequest.getChooseType())
                .balanceChange(transactionModel.getBalanceChange())
                .build();

        //if person does not have enough balance, then the newly created Transaction will now be deleted and throw exception
        if(transUpdate.getChooseType().equals(TransactionModel.Choose.exchange)){
            if(!balanceService.checkBalanceForExchange(transUpdate)){
                deleteTransactionByNo(transactionModel.getId());
                throw new CustomException("You do not have enough balance");
            }
            balanceService.updateBalanceByTransaction(transUpdate);
            storageService.updateStorageByTransaction(machineModel, batType, batteriesExchanged);
        }else {
            balanceService.updateBalanceByTransaction(transUpdate);
        }
        return ResponseEntity.ok(new GeneralResponse("Update successful"));
    }

    //Delete the Transaction created during vending machine flow if person choose to cancel transaction
    public void deleteTransactionByNo(Long id) throws Exception{
        //delete all TransactionEntryModels linked to current Transaction, then delete Transaction
        List<TransactionEntryModel> transactionEntryModelList = transactionEntryRepo.getTransactionEntryByTransactionId(id)
                .orElseThrow(() -> new CustomException("Unable to find transaction entries from this id"));
        for (TransactionEntryModel te: transactionEntryModelList
             ) {
            transactionEntryRepo.delete(te);
        }

        TransactionModel transactionModel = transactionRepo.getById(id);
        transactionRepo.delete(transactionModel);
    }

    //fall back method to allow manual update of Transaction
    public void updateTransaction(TransactionRequest transactionRequest, Long id) throws Exception{
        TransactionModel transactionModel = transactionRepo.findById(id).orElseThrow(() -> new CustomException("Transaction not found."));//get the data bases on primary key

        if (transactionRequest.getPeopleId() != null) {
            transactionModel.setPeopleModel(peopleService.getPeopleById(transactionRequest.getPeopleId()));
        }
        if (transactionRequest.getMachineId() != null) {
            transactionModel.setMachineModel(machineService.getMachineById(transactionRequest.getMachineId()));
        }
        if (transactionRequest.getDateAndTime() != null && !transactionRequest.getDateAndTime().equals("")) {
            transactionModel.setDateAndTime(transactionRequest.getDateAndTime());
        }
        if (transactionRequest.getChooseType() != null) {
            transactionModel.setChoose(transactionRequest.getChooseType());
        }
    }

}
