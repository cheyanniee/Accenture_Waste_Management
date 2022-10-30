package com.backend.controller;

import com.backend.configuration.CustomException;
import com.backend.request.BatteryRequest;
import com.backend.response.GeneralResponse;
import com.backend.service.BatteryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("dev/v1/battery")
public class BatteryController {

    @Autowired
    BatteryService batteryService;

    @GetMapping("listall")
    public ResponseEntity<?> listAll() {
        return ResponseEntity.ok(batteryService.listAllBattery());
    }


    @PostMapping("create")
    public ResponseEntity<?> createBattery(@RequestBody BatteryRequest batteryRequest) throws CustomException {
        batteryService.createBattery(batteryRequest);
        return ResponseEntity.ok(new GeneralResponse("Battery created successfully!"));
    }

    @PostMapping("update")
    public ResponseEntity<?> updateBattery(@RequestBody BatteryRequest batteryRequest) throws CustomException {
        batteryService.updateBattery(batteryRequest);
        return ResponseEntity.ok(new GeneralResponse("Battery " + batteryRequest.getType() + " updated successfully!"));
    }

}
