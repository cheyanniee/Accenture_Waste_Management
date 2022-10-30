package com.backend.service;

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

    public Optional<MachineModel> findMachineById(Integer id){return machineRepo.findById(id);}
    //check with whoever on Machine to do method to find Machine from Id
}
