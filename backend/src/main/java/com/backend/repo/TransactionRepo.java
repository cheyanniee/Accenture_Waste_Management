package com.backend.repo;

import com.backend.model.TransactionModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface TransactionRepo extends JpaRepository<TransactionModel, Long> {

    @Query("select transaction from TransactionModel transaction where people_id=?1")
    public Optional <List <TransactionModel>> getTransactionByPeopleId (Long people_id);

//    @Query("select transaction from TransactionModel transaction where machine_id=?1")
//    public TransactionModel getTransactionsByMachine(Long id);
//    //query from list using machineId
//    //get the last transaction from table
//
//    @Query("select transaction from TransactionModel transaction order by id desc limit 1")
//    public TransactionModel getLastTransaction();






}
