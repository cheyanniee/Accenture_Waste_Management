package com.backend.controller;

import com.backend.service.MachineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("dev/v1/machine")
public class MachineController {

    @Autowired
    MachineService machineService;

    @GetMapping("listall")
    public ResponseEntity<?> listAll() {
        return ResponseEntity.ok(machineService.listAllMachine());
    }

    //machine status - faultiness (changing)

    //current storage/capacity = %

    //machine to mark as collected

}
