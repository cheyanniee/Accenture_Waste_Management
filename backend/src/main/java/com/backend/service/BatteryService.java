package com.backend.service;

import com.backend.model.BatteryModel;
import com.backend.repo.BatteryRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BatteryService {

    @Autowired
    BatteryRepo batteryRepo;

    public List<BatteryModel> listAllBattery() {
        return batteryRepo.findAll();
    }
}
