package com.backend.controller;

import com.backend.configuration.CustomException;
import com.backend.model.MachineModel;
import com.backend.request.MachineRequest;
import com.backend.response.GeneralResponse;
import com.backend.service.MachineService;

import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

@RestController
@RequestMapping("dev/v1/machine")
public class MachineController {

    @Autowired
    MachineService machineService;

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
    // machine status - faultiness (changing)
    // current storage/capacity = %
    // machine to mark as collected

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
    // delete machine
    // update currentLoad(from vending machine side)
    // send email notifications if current load reach 80%

}
