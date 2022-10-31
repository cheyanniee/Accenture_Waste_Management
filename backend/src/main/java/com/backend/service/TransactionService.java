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


    //use which one?
    public Long createTransactionWithReturn(){
        TransactionModel transactionModel = TransactionModel.builder()
                .balanceChange(0F)
                .build();
        transactionRepo.save(transactionModel);
        return transactionModel.getId();
    }

//    public TransactionModel getLastTransaction (){
//        return transactionRepo.getLastTransaction();
//    }

    public TransactionModel createTempTransactionWithParams (String token, TransactionModel.Choose choose) throws Exception{
        TransactionModel transactionModel = TransactionModel.builder()
                .peopleModel(peopleService.getPeopleById(peopleService.getIdByToken(token)))
                .choose(choose)
                .balanceChange(0F)
                .build();
        transactionRepo.save(transactionModel);
        return transactionModel;
    }

    public TransactionModel createTempTransactionWithReturn () {
        TransactionModel transactionModel = TransactionModel.builder()
                .balanceChange(0F)
                .build();
        transactionRepo.save(transactionModel);
        return transactionModel;
    }

    //transactionEntryRequest is used to check if the currentTransaction exist. If not, create, then return the newly created Transction
    //by right the newly created Transaction should have the sameId as requested in the transactionEntryRequest.
    public TransactionModel checkTransactionIsExist(TransactionEntryRequest transactionEntryRequest)
            throws CustomException{
        try{
            return transactionRepo.findById(transactionEntryRequest.getTransactionId()).orElseThrow(() -> new CustomException("Unable to check if exist"));
        }catch (Exception e){
            createTempTransactionWithReturn();
            return transactionRepo.findById(transactionEntryRequest.getTransactionId()).orElseThrow(()
                    -> new CustomException("Not in sequence hence not created (too much difference between transactionEntryRequest's id and the newly created id"));
        }
    }

    public void saveChooseType(TransactionModel transactionModel){
        transactionRepo.save(transactionModel);
    }

//    //changed to updateTransactionByYesExchange
//    public ResponseEntity<?> updateTransactionByYes (TransactionRequest transactionRequest, Long id) throws Exception {
//
//        TransactionModel transactionModel = getTransaction(id);
//
//        ZoneId zid = ZoneId.of("Asia/Singapore");
//
//        PeopleModel peopleModel = peopleService.getPeopleById(transactionRequest.getPeopleId());
//        MachineModel machineModel = machineService.getMachineById(transactionRequest.getMachineId());
//
//        List<TransactionEntryModel> transactionEntryModelList =
//                transactionEntryRepo.getTransactionEntryByTransactionId(id)
//                        .orElseThrow(()-> new CustomException("Unable to find list of transactionEntries as no Transaction exists."));
//
//
//        Float balanceChange = 0F;
//        for (TransactionEntryModel teM: transactionEntryModelList) {
//            if (transactionModel.getChoose().equals(TransactionModel.Choose.recycle)){
//                Float pointsOne = teM.getBatteryModel().getValuePerWeight() * teM.getQuantity();
//                balanceChange += pointsOne;
//            }
//            if(transactionModel.getChoose().equals(TransactionModel.Choose.exchange)){
//                Float exchangeOne = teM.getRateModel().getPointsPerUnit() * teM.getQuantity();
//                balanceChange += exchangeOne;
//            }
//        }
//
//        transactionModel.setPeopleModel(peopleModel);
//        transactionModel.setMachineModel(machineModel);
//        transactionModel.setBalanceChange(balanceChange);
//        transactionModel.setChoose(transactionRequest.getChooseType());
//        saveChooseType(transactionModel);
//        transactionModel.setDateAndTime(ZonedDateTime.now(zid));
//        transactionRepo.save(transactionModel);
//
//        //pass the updated balance to updateBalance as a new TransactionRequest.
//        TransactionRequest transUpdate = TransactionRequest.builder()
//                .peopleId(transactionModel.getPeopleModel().getId())
//                .chooseType(transactionRequest.getChooseType())
//                .balanceChange(transactionModel.getBalanceChange())
//                .build();
//
//            if(transUpdate.getChooseType().equals(TransactionModel.Choose.exchange)
//                    && !balanceService.checkBalanceForExchange(transUpdate)){
//                deleteTransactionByNo(transactionModel.getId());
//                throw new CustomException("You do not have enough balance");
//            }else {
//                balanceService.updateBalanceByTransaction(transUpdate);
//            }
//
//        return ResponseEntity.ok(new GeneralResponse("Update successful"));
//    }

//    public void createTransactionBackupCopy (TransactionRequest transactionRequest) throws Exception {
//        ZoneId zid = ZoneId.of("Asia/Singapore");
//
//        PeopleModel peopleModel = peopleService.getPeopleById(transactionRequest.getPeopleId());
//        MachineModel machineModel = machineService.findMachineById(transactionRequest.getMachineId()).orElseThrow(()-> new CustomException("Unable to find machine"));
//        TransactionModel transactionModel = TransactionModel.builder()
//                .peopleModel(peopleModel)
//                .machineModel(machineModel)
//                .balanceChange(transactionRequest.getBalanceChange())
//                .dateAndTime(ZonedDateTime.now(zid)) //check this point against the other timestamps
//                .build();
//        transactionRepo.save(transactionModel);
//        try{
//            balanceService.updateBalance(transactionRequest);
//        }catch (Exception e){
//            e.getMessage();
//        }
//    }

    //delete transaction but also all the linked transactionEntries?
    public void deleteTransactionByNo(Long id) throws Exception{
        //check if deleting a transaciton will also delete its linked transactionEntries
        List<TransactionEntryModel> transactionEntryModelList = transactionEntryRepo.getTransactionEntryByTransactionId(id)
                .orElseThrow(() -> new CustomException("Unable to find transaction entries from this id"));
        for (TransactionEntryModel te: transactionEntryModelList
             ) {
            transactionEntryRepo.delete(te);
        }
        TransactionModel transactionModel = transactionRepo.getById(id);
        transactionRepo.delete(transactionModel);
    }


    //manual update Transaction if needed
    //to add in Admin token verification
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



    //experiment with storage
    public ResponseEntity<?> updateTransactionByYesExchange (TransactionRequest transactionRequest, Long id, String token) throws Exception {


        TransactionModel transactionModel = getTransaction(id);

        ZoneId zid = ZoneId.of("Asia/Singapore");


        //PeopleModel peopleModel = peopleService.getPeopleById(transactionRequest.getPeopleId());
        PeopleModel peopleModel = peopleService.getPeopleById(peopleService.getIdByToken(token));
        MachineModel machineModel = machineService.getMachineById(transactionRequest.getMachineId());

        List<TransactionEntryModel> transactionEntryModelList =
                transactionEntryRepo.getTransactionEntryByTransactionId(id)
                        .orElseThrow(()-> new CustomException("Unable to find list of transactionEntries as no Transaction exists."));


        Float balanceChange = 0F;

        String rateType = "";
        Float exchangePointsRate = 0F;
        for (TransactionEntryModel teM: transactionEntryModelList) {
            if(transactionModel.getChoose().equals(TransactionModel.Choose.exchange)){
                rateType = teM.getRateModel().getType();
                System.out.print("Rate type is: " + rateType);
                //still keep this but change to just getBattery, then which column - pointsperunit or valueperweight
                exchangePointsRate = teM.getRateModel().getPointsPerUnit();
                Float exchangeOne = teM.getRateModel().getPointsPerUnit() * teM.getQuantity();
                balanceChange += exchangeOne;
            }
            if (transactionModel.getChoose().equals(TransactionModel.Choose.recycle)){
                Float pointsOne = teM.getBatteryModel().getRecyclePoint() * teM.getQuantity();
                balanceChange += pointsOne;
            }
        }

        //needs to be in integer because cannot exchange 1.5 battery
        Integer batteriesExchanged = Math.round(balanceChange/exchangePointsRate);

        StorageModel storageModel = storageService.getStorageByMachineId(machineModel);
        //Storage to check if points and number of storage enough

        //check this part with new battery model
        //right now storage only got AA and AAA so this part should be okay.
        Integer batteriesStored = 0;
        if (rateType.equals("AAA")){
            batteriesStored = storageModel.getQtyAAA();
            System.out.print("batteries stored in AAA: " + batteriesStored);
        }
        else if (rateType.equals("AA")){
            batteriesStored = storageModel.getQtyAAA();
            System.out.print("batteries stored in AA: " + batteriesStored);
        }
        //if not enough batteries then immediately throw error and start over in first page.
        if(batteriesStored < batteriesExchanged){
            throw new CustomException("Not enough batteries of this type in storage. Please start over");
        }

        transactionModel.setPeopleModel(peopleModel);
        transactionModel.setMachineModel(machineModel);
        transactionModel.setBalanceChange(balanceChange);
        transactionModel.setChoose(transactionRequest.getChooseType());
        saveChooseType(transactionModel);
        transactionModel.setDateAndTime(ZonedDateTime.now(zid));
        transactionRepo.save(transactionModel);

        //pass the updated balance to updateBalance as a new TransactionRequest.
        TransactionRequest transUpdate = TransactionRequest.builder()
                .peopleId(transactionModel.getPeopleModel().getId())
                .chooseType(transactionRequest.getChooseType())
                .balanceChange(transactionModel.getBalanceChange())
                .build();

        if(transUpdate.getChooseType().equals(TransactionModel.Choose.exchange)){
            if(!balanceService.checkBalanceForExchange(transUpdate)){
                deleteTransactionByNo(transactionModel.getId());
                throw new CustomException("You do not have enough balance");
            }
            balanceService.updateBalanceByTransaction(transUpdate);
            storageService.updateStorageByTransaction(machineModel, rateType, batteriesExchanged);
        }else {
            balanceService.updateBalanceByTransaction(transUpdate);
        }
        return ResponseEntity.ok(new GeneralResponse("Update successful"));
    }

}
