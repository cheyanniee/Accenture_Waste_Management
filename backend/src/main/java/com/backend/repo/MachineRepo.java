package com.backend.repo;

import com.backend.model.MachineModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

public interface MachineRepo extends JpaRepository<MachineModel, Integer> {

    //update status depending on the argument given?

    @Modifying
    @Transactional
    @Query("update MachineModel set status = ?1 where id = ?2")
    Integer updateStatusByMachineId(String status, Long machineId);




}
