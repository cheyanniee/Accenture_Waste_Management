package com.backend.repo;

import com.backend.model.MachineModel;
import com.backend.model.MachineModel.Status;

import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface MachineRepo extends JpaRepository<MachineModel, Integer> {
  @Query("SELECT machine from MachineModel machine WHERE id=?1")
  Optional<MachineModel> getMachineById(int machineId);

  @Modifying
  @Transactional
  @Query("UPDATE MachineModel SET current_load=?1 WHERE id=?2")
  Integer updateCurrentLoad(Float currentLoad, Integer machineId);

  @Modifying
  @Transactional
  @Query("UPDATE MachineModel SET status=?1 WHERE id=?2")
  Integer updateStatus(Status status, Integer machineId);
}
