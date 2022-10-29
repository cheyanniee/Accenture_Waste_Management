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
        if(listDeleted.size()==1){
            return ResponseEntity.ok(new GeneralResponse("Battery " + listDeleted.get(0).getType() + " deleted!"));
        }else if(listDeleted.size()>1){
            throw new CustomException("A few battery with type deleted.");
        }else{
            throw new CustomException("No battery deleted.");
        }

    }

}
