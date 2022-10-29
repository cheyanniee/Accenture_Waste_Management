package com.backend.service;

import com.backend.configuration.CustomException;
import com.backend.model.LocationModel;
import com.backend.model.MachineModel;
import com.backend.model.PeopleModel;
import com.backend.model.PeopleModel.Role;
import com.backend.repo.MachineRepo;
import com.backend.request.MachineRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class MachineService {

    @Autowired
    MachineRepo machineRepo;

    @Autowired
    PeopleService peopleService;

    @Autowired
    LocationService locationService;

    public List<MachineModel> listAllMachine() {
        return machineRepo.findAll();
    }

    public MachineModel getMachineById(int machineId) throws CustomException {
        return machineRepo.getMachineById(machineId)
                .orElseThrow(() -> new CustomException("No Machine with id: " + machineId + " found"));
    }

    public boolean addMachine(MachineRequest machineRequest)
            throws CustomException, NumberFormatException {
        LocationModel machineLocation = findMachineLocationById(machineRequest.getLocationId());
        MachineModel newMachine = MachineModel.builder()
                .name(machineRequest.getName())
                .currentLoad(machineRequest.getCurrentLoad())
                .capacity(machineRequest.getCapacity())
                .status(machineRequest.getStatus())
                .machinelocation(machineLocation)
                .unitNumber(machineRequest.getUnitNumber())
                .build();

        machineRepo.save(newMachine);
        return true;
    }

    public PeopleModel getAdminByToken(String token) throws CustomException {
        PeopleModel admin = peopleService.findPeople(peopleService.getIdByToken(token))
                .orElseThrow(() -> new CustomException("Admin not found!"));
        if (admin.getRole() != Role.admin)
            throw new CustomException("User do not have enough access rights to perform this operation!");

        return admin;
    }

    private LocationModel findMachineLocationById(Long locationId) throws CustomException {
        List<LocationModel> locationList = locationService.listAllLocation();
        return locationList.stream()
                .filter(location -> Objects.equals(location.getId(), locationId))
                .findAny()
                .orElseThrow(() -> new CustomException("Location not found"));

    }
}
