package com.backend.repo;

import com.backend.model.StorageModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface StorageRepo extends JpaRepository<StorageModel, Integer> {

    @Query("select storage from StorageModel storage where machine_id=?1")
    Optional<StorageModel> getStorageByMachineId(Integer machine_id);

}
