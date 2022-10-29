package com.backend.repo;

import com.backend.model.TransactionModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionRepo extends JpaRepository<TransactionModel, Long> {
}
