package com.backend.service;

import com.backend.configuration.CustomException;
import com.backend.model.LocationModel;
import com.backend.model.MachineModel;
import com.backend.model.PeopleModel;
import com.backend.model.PeopleModel.Role;
import com.backend.repo.MachineRepo;
import com.backend.request.EmailRequest;
import com.backend.request.LocationRequest;
import com.backend.request.MachineRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import java.util.List;
import java.util.Objects;

/*
Purpose:
    - Define service to list all machine entries in machine table
    - Define service to get machine by machineId
    - Define service to create, update, delete machine in machine table

Author:
    - Alex Lim
 */

@Service
public class MachineService {

    @Autowired
    MachineRepo machineRepo;

    @Autowired
    PeopleService peopleService;

    @Autowired
    LocationService locationService;

    @Autowired
    EmailService emailService;

    @Autowired
    StorageService storageService;

    @Autowired
    private Environment env;

    private static final Float WARNING_FULL = 80F;

    public List<MachineModel> listAllMachine() {
        return machineRepo.findAll();
    }

    public MachineModel getMachineById(int machineId) throws CustomException {
        return machineRepo.getMachineById(machineId)
                .orElseThrow(() -> new CustomException("No Machine with id: " + machineId + " found"));
    }

    public boolean addMachine(MachineRequest machineRequest)
            throws CustomException, NumberFormatException {
        LocationRequest location = LocationRequest.builder()
                .address(machineRequest.getAddress())
                .postcode(machineRequest.getPostcode())
                .build();
        LocationModel machineLocation = locationService.bindLocation(location);

        MachineModel newMachine = MachineModel.builder()
                .name(machineRequest.getName())
                .currentLoad(machineRequest.getCurrentLoad())
                .capacity(machineRequest.getCapacity())
                .status(machineRequest.getStatus())
                .machinelocation(machineLocation)
                .unitNumber(machineRequest.getUnitNumber())
                .build();

        machineRepo.save(newMachine);
        storageService.createStorage(newMachine); // to ensure Storage is created and linked to Machine when created
        return true;
    }

    public boolean updateMachineStatus(MachineRequest machineRequest) throws CustomException, MessagingException {
        MachineModel machine = getMachineById(machineRequest.getMachineId());

        if (machineRequest.getName() != null && !machineRequest.getName().equals(""))
            machine.setName(machineRequest.getName());
        if (machineRequest.getCurrentLoad() != null && !machineRequest.getCurrentLoad().isNaN()) {
            machine.setCurrentLoad(machineRequest.getCurrentLoad());
            checkBalanceCapacity(machine);
        }
        if (machineRequest.getCapacity() != null && !machineRequest.getCapacity().isNaN())
            machine.setCapacity(machineRequest.getCapacity());
        if (machineRequest.getStatus() != null && !machineRequest.getStatus().equals(""))
            machine.setStatus(machineRequest.getStatus());
        if (machineRequest.getUnitNumber() != null && !machineRequest.getUnitNumber().equals(""))
            machine.setUnitNumber(machineRequest.getUnitNumber());
        if ((machineRequest.getPostcode() != null && !machineRequest.getPostcode().equals("")) &&
                (machineRequest.getAddress() != null && !machineRequest.getAddress().equals(""))) {
            LocationRequest locationRequest = LocationRequest.builder()
                    .address(machineRequest.getAddress())
                    .postcode(machineRequest.getPostcode())
                    .build();
            machine.setMachinelocation(locationService.bindLocation(locationRequest));
        }

        machineRepo.save(machine);
        return true;
    }

    // public boolean deleteMachine(int machineId) throws CustomException {
    // MachineModel machine = getMachineById(machineId);
    // machineRepo.delete(machine);
    // return true;
    // }

    public PeopleModel getAdminByToken(String token) throws CustomException {
        PeopleModel admin = peopleService.findPeople(peopleService.getIdByToken(token))
                .orElseThrow(() -> new CustomException("Admin not found!"));
        if (admin.getRole() != Role.admin)
            throw new CustomException("User do not have enough access rights to perform this operation!");

        return admin;
    }

    public LocationModel findMachineLocationById(Long locationId) throws CustomException {
        List<LocationModel> locationList = locationService.listAllLocation();
        return locationList.stream()
                .filter(location -> Objects.equals(location.getId(), locationId))
                .findAny()
                .orElseThrow(() -> new CustomException("Location not found"));
    }

    public boolean updateCurrentLoad(MachineRequest machineRequest) throws CustomException, MessagingException {
        MachineModel machine = getMachineById(machineRequest.getMachineId());
        Float inputLoad = machineRequest.getCurrentLoad();
        if (machine.getCapacity() < inputLoad) {
            throw new CustomException("Machine will be overloaded!");
        }
        machine.setCurrentLoad(inputLoad);
        machineRepo.updateCurrentLoad(machine.getCurrentLoad(), machine.getId());
        checkBalanceCapacity(machine);
        return true;
    }

    // Update machine status eg. NORMAL/FAULTY
    public boolean updateStatus(MachineRequest machineRequest) throws CustomException {
        MachineModel machine = getMachineById(machineRequest.getMachineId());
        machineRepo.updateStatus(machineRequest.getStatus(), machine.getId());
        return true;
    }

    private void checkBalanceCapacity(MachineModel machine) throws MessagingException {
        Float balanceLoad = machine.getCurrentLoad() / machine.getCapacity() * 100;
        if (balanceLoad >= WARNING_FULL)
            sendEmailNotification(machine, balanceLoad);
    }

    // send email notifications if current load reach 80%
    private boolean sendEmailNotification(MachineModel machine, Float balanceLoad) throws MessagingException {
        System.out.println("Sending email notifications");

        EmailRequest email = EmailRequest.builder()
                .recipient(env.getProperty("ADMIN_EMAIL"))
                .subject("Machine >= 80% load")
                .msgBody("Machine ID: " + machine.getId() + " is " + balanceLoad + "% full")
                .build();

        emailService.sendMail(email);
        return true;
    }
}
