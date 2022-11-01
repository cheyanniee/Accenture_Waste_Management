package com.backend.repo;

import com.backend.model.BatteryModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/*
    Purpose:
        - Repository to define JPA queries for battery table

    Author:
        - Liu Fang
*/

@Repository
public interface BatteryRepo extends JpaRepository<BatteryModel, Integer>{

    @Query("select battery from BatteryModel battery where type=?1")
    Optional<BatteryModel> getBatteryByType(String type);

    @Modifying
    @Transactional
    @Query("delete from BatteryModel where id = ?1")
    Integer deleteBatteryById(Integer id);

    @Transactional
    List<BatteryModel> deleteByType(String type);
}
