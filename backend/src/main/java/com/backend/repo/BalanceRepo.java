package com.backend.repo;

import com.backend.model.BalanceModel;
import com.backend.model.PeopleModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.time.ZonedDateTime;
import java.util.Optional;

public interface BalanceRepo extends JpaRepository<BalanceModel, Long> {

    @Query("select balance from BalanceModel balance where people_id=?1")
    Optional<BalanceModel> getBalanceByPeopleId(Long people_id);

    @Modifying
    @Transactional
    @Query("update BalanceModel set current_balance = ?1 where id = ?2")
    Integer updateBalanceById(Float currentBalance, Long balanceId);

}
