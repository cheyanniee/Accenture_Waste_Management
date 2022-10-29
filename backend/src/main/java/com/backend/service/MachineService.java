package com.backend.service;

import com.backend.configuration.CustomException;
import com.backend.model.MachineModel;
import com.backend.repo.MachineRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MachineService {

    @Autowired
    MachineRepo machineRepo;

    public List<MachineModel> listAllMachine() {
        return machineRepo.findAll();
    }

    public MachineModel getMachineById(int machineId) throws CustomException {
        return machineRepo.getMachineById(machineId)
                .orElseThrow(() -> new CustomException("No Machine with id: " + machineId + " found"));
    }
}
