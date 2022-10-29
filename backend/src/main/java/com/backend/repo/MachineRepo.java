package com.backend.repo;

import com.backend.model.MachineModel;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface MachineRepo extends JpaRepository<MachineModel, Integer> {
  @Query("SELECT machine from MachineModel machine WHERE id=?1")
  Optional<MachineModel> getMachineById(int machineId);
}
