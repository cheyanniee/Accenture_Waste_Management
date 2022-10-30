package com.backend.service;


import com.backend.model.MachineModel;
import com.backend.model.StorageModel;
import com.backend.repo.StorageRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StorageService {

    @Autowired
    StorageRepo storageRepo;

    public List<StorageModel> listStorage() {
        return storageRepo.findAll();
    }

    public StorageModel getStorageByMachineId(MachineModel machineModel) throws Exception {
        return storageRepo.getStorageByMachineId(machineModel.getId()).orElseThrow(()-> new Exception("Unable to find balance"));
    }

    // create balance alongside people
    public void createBalance (MachineModel machineModel) {
        StorageModel storageModel = StorageModel.builder()
                .machineModel(machineModel)
                .qtyAA(0)
                .qtyAAA(0)
                .build();
        storageRepo.save(storageModel);
    }

    //delete storage using machine id
    public void deleteStorage (MachineModel machineModel) throws Exception {
        StorageModel storageModel = storageRepo.getStorageByMachineId(machineModel.getId()).orElseThrow(() -> new Exception("Unable to find storage of machine"));
        storageRepo.delete(storageModel);
    }

    public void updateStorageByTransaction (MachineModel machineModel, String type, Integer batteriesExchange) throws Exception{
        StorageModel storageModel = storageRepo.getStorageByMachineId(machineModel.getId()).orElseThrow(() -> new Exception("Unable to find storage of machine"));
        Integer batteriesStored = 0;
        if (type.equals("AA")){
            batteriesStored = storageModel.getQtyAA();
            storageModel.setQtyAA(batteriesStored-batteriesExchange);
        }
        if (type.equals("AAA")){
            batteriesStored = storageModel.getQtyAAA();
            storageModel.setQtyAAA(batteriesStored-batteriesExchange);
        }
        storageRepo.save(storageModel);
    }


    //updateMachineStorage after exchange happens

    //check storage

}
