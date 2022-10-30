package com.backend.service;

import com.backend.configuration.CustomException;
import com.backend.model.BatteryModel;
import com.backend.repo.BatteryRepo;
import com.backend.request.BatteryRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class BatteryService {

    @Autowired
    BatteryRepo batteryRepo;

    public List<BatteryModel> listAllBattery() {
        return batteryRepo.findAll();
    }

    public void createBattery(BatteryRequest batteryRequest) throws CustomException {
        Optional<BatteryModel> batteryExist = batteryRepo.getBatteryByType(batteryRequest.getType());
        if (batteryExist.isPresent()) {
            throw new CustomException("Battery type exists.");
        }

        ZoneId zid = ZoneId.of("Asia/Singapore");
        ZonedDateTime dtCreate = ZonedDateTime.now(zid);

        BatteryModel batteryModel = BatteryModel.builder()
                .type(batteryRequest.getType())
                .valuePerWeight(batteryRequest.getValuePerWeight())
                .lastUpdate(dtCreate)
                .build();

        batteryRepo.save(batteryModel);
    }

    public boolean updateBattery(BatteryRequest batteryRequest) throws CustomException {
        BatteryModel battery = batteryRepo.findById(batteryRequest.getId()).orElseThrow(() -> new CustomException("Battery is not found!"));
        if (batteryRequest.getType() != null && !batteryRequest.getType().equals("")) {
            battery.setType(batteryRequest.getType());
        }

        if (batteryRequest.getValuePerWeight() != null && batteryRequest.getValuePerWeight() != 0) {
            battery.setValuePerWeight(batteryRequest.getValuePerWeight());
        }

        ZoneId zid = ZoneId.of("Asia/Singapore");
        ZonedDateTime dtUpdate = ZonedDateTime.now(zid);

        battery.setLastUpdate(dtUpdate);
        batteryRepo.save(battery);
        return true;
    }

//    public Integer deleteBattery(BatteryRequest batteryRequest){
//       return batteryRepo.deleteBatteryById(batteryRequest.getId());
//    }

    public List<BatteryModel> deleteBattery(BatteryRequest batteryRequest){
        return batteryRepo.deleteByType(batteryRequest.getType());
    }


}
