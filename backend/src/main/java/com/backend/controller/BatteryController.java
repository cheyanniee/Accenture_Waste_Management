package com.backend.controller;

import com.backend.configuration.CustomException;
import com.backend.model.BatteryModel;
import com.backend.request.BatteryRequest;
import com.backend.response.GeneralResponse;
import com.backend.service.BatteryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

//Purpose:
//    - List all rows of data from battery table in Database
//    - Create, Update, Delete battery entry from Database
//    - To add token Interceptors
//    - To resolve CORS error
//
//Restrictions:
//    - Only the logged in admin user can have access to these URLs
//
//Endpoints:
//        - /dev/v1/battery/listall
//        - /dev/v1/battery/create
//        - /dev/v1/battery/update
//        - /dev/v1/battery/delete
//
//Author:
//    - Liu Fang


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
    public ResponseEntity<?> create(@RequestBody BatteryRequest batteryRequest) throws CustomException {
        batteryService.createBattery(batteryRequest);
        return ResponseEntity.ok(new GeneralResponse("Battery created successfully!"));
    }

    @PostMapping("update")
    public ResponseEntity<?> update(@RequestBody BatteryRequest batteryRequest) throws CustomException {
        batteryService.updateBattery(batteryRequest);
        return ResponseEntity.ok(new GeneralResponse("Battery " + batteryRequest.getType() + " updated successfully!"));
    }

    @PostMapping("delete")
    public ResponseEntity<?> delete(@RequestBody BatteryRequest batteryRequest) throws CustomException {
        List<BatteryModel> listDeleted = batteryService.deleteBattery(batteryRequest);
        if (listDeleted.size() == 1) {
            return ResponseEntity.ok(new GeneralResponse("Battery " + listDeleted.get(0).getType() + " deleted!"));
        } else if (listDeleted.size() > 1) {
            throw new CustomException("A few type of " + listDeleted.get(0).getType() + " deleted.");
        } else {
            throw new CustomException("No battery deleted.");
        }

    }

}
