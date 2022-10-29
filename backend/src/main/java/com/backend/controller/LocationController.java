package com.backend.controller;

import com.backend.request.LocationRequest;
import com.backend.response.GeneralResponse;
import com.backend.service.LocationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("dev/v1/location")
public class LocationController {

    @Autowired
    LocationService locationService;


    @GetMapping("listall")
    public ResponseEntity<?> listAll() {
        return ResponseEntity.ok(locationService.listAllLocation());
    }

    @PostMapping("register")
    public ResponseEntity<?> registerLocation(@RequestBody LocationRequest locationRequest) {
        locationService.createLocation(locationRequest);
        return ResponseEntity.ok(new GeneralResponse("New Location Registered!"));
    }

}
