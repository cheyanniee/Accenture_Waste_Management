package com.backend.controller;

import com.backend.configuration.CustomException;
import com.backend.model.MachineModel;
import com.backend.request.MachineRequest;
import com.backend.response.GeneralResponse;
import com.backend.service.LocationService;
import com.backend.service.MachineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;
import java.util.List;

//Purpose:
//    - Create URLs to listall machines, and CRUD operations of machine entry in machine table of DB
//    - Create URLs to update load and the status of machine
//
//Restrictions:
//    - Only logged-in user can have access to CRUD operations.
//
//Endpoints:
//      - /dev/v1/machine/listall
//      - /dev/v1/machine/get/{machineId}
//      - /dev/v1/machine/add
//      - /dev/v1/machine/update
//      - /dev/v1/machine/delete/{machineId}
//      - /dev/v1/machine/update/currentload
//      - /dev/v1/machine/update/status
//
//Author:
//    - Alex Lim

@RestController
@RequestMapping("dev/v1/machine")
public class MachineController {

    @Autowired
    MachineService machineService;

    @Autowired
    LocationService locationService;

    @GetMapping("listall")
    public ResponseEntity<List<MachineModel>> listAll() {
        return ResponseEntity.ok(machineService.listAllMachine());
    }

    //
    @GetMapping("/get/{machineId}")
    public ResponseEntity<MachineModel> getMachineById(@PathVariable String machineId)
            throws NumberFormatException, CustomException {
        System.out.println("machine id: " + machineId);
        return ResponseEntity.ok(machineService.getMachineById(Integer.valueOf(machineId)));
    }

    // create machines
    @PostMapping("/add")
    public ResponseEntity<GeneralResponse> postMethodName(@RequestHeader String token,
            @RequestBody MachineRequest machineRequest) throws CustomException {
        // check if it's admin
        machineService.getAdminByToken(token);
        machineService.addMachine(machineRequest);
        return ResponseEntity.ok(new GeneralResponse("Machine Added!"));
    }

    // update status
    @PostMapping("/update")
    public ResponseEntity<GeneralResponse> machineUpdate(@RequestHeader String token,
            @RequestBody MachineRequest machineRequest) throws CustomException, MessagingException {
        machineService.getAdminByToken(token);
        machineService.updateMachineStatus(machineRequest);
        return ResponseEntity.ok(new GeneralResponse("Update success!"));
    }

    // delete machine
    @DeleteMapping("/delete/{machineId}")
    public ResponseEntity<GeneralResponse> deleteMachine(@RequestHeader String token, @PathVariable String machineId)
            throws NumberFormatException, CustomException {
        machineService.getAdminByToken(token);
        machineService.deleteMachine(Integer.valueOf(machineId));
        return ResponseEntity.ok(new GeneralResponse("Machine Deleted!"));
    }

    // update currentLoad(from vending machine side)
    @PostMapping("/update/currentload")
    public ResponseEntity<GeneralResponse> updateCurrentLoad(@RequestHeader String token,
            @RequestBody MachineRequest machineRequest) throws CustomException, MessagingException {
        machineService.updateCurrentLoad(machineRequest);
        return ResponseEntity.ok(new GeneralResponse("Current Load Updated!"));
    }

    // update machine Status
    @PostMapping("/update/status")
    public ResponseEntity<GeneralResponse> updateStatus(@RequestHeader String token,
            @RequestBody MachineRequest machineRequest) throws CustomException {
        machineService.updateStatus(machineRequest);
        return ResponseEntity.ok(new GeneralResponse("Status updated!"));
    }
}
