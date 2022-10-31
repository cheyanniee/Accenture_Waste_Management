package com.backend.repo;

import com.backend.model.TransactionModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

/*
    Purpose:
        - Repo to execute SQL queries for Transaction

    Author:
        - Lew Xu Hong
*/
public interface TransactionRepo extends JpaRepository<TransactionModel, Long> {

    @Query("select transaction from TransactionModel transaction where people_id=?1")
    public Optional <List <TransactionModel>> getTransactionByPeopleId (Long people_id);

}
