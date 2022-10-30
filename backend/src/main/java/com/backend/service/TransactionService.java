package com.backend.service;

import com.backend.configuration.CustomException;
import com.backend.model.*;
import com.backend.repo.BalanceRepo;
import com.backend.repo.TransactionEntryRepo;
import com.backend.repo.TransactionRepo;
import com.backend.request.LocationRequest;
import com.backend.request.PeopleRequest;
import com.backend.request.TransactionEntryRequest;
import com.backend.request.TransactionRequest;
import com.backend.response.GeneralResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.*;

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

    //rework into updating
    public ResponseEntity<?> updateTransactionByYes (TransactionRequest transactionRequest, Long id) throws Exception {

        TransactionModel transactionModel = getTransaction(id);

        ZoneId zid = ZoneId.of("Asia/Singapore");

        PeopleModel peopleModel = peopleService.getPeopleById(transactionRequest.getPeopleId());
        MachineModel machineModel = machineService.getMachineById(transactionRequest.getMachineId());

        List<TransactionEntryModel> transactionEntryModelList =
                transactionEntryRepo.getTransactionEntryByTransactionId(id)
                        .orElseThrow(()-> new CustomException("Unable to find list of transactionEntries as no Transaction exists."));

        Float balanceChange = 0F;
        for (TransactionEntryModel teM: transactionEntryModelList) {
            if (transactionModel.getChoose().equals(TransactionModel.Choose.recycle)){
                Float pointsOne = teM.getBatteryModel().getValuePerWeight() * teM.getQuantity();
                balanceChange += pointsOne;
            }
            if(transactionModel.getChoose().equals(TransactionModel.Choose.exchange)){
                Float exchangeOne = teM.getRateModel().getPointsPerUnit() * teM.getQuantity();
                balanceChange += exchangeOne;
            }
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

            if(transUpdate.getChooseType().equals(TransactionModel.Choose.exchange) && !balanceService.checkBalanceForExchange(transUpdate)){
                deleteTransactionByNo(transactionModel.getId());
                throw new CustomException("You do not have enough balance");
            }else {
                balanceService.updateBalanceByTransaction(transUpdate);
            }

        return ResponseEntity.ok(new GeneralResponse("Update successful"));
    }

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

}
