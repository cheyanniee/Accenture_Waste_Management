package com.backend.controller;


import com.backend.model.MachineModel;
import com.backend.model.PeopleModel;
import com.backend.request.StorageRequest;
import com.backend.response.GeneralResponse;
import com.backend.service.MachineService;
import com.backend.service.PeopleService;
import com.backend.service.StorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/*
    Purpose:
        - APIs for StorageModel-related operations
        - Allow People (Admin) to view all existing StorageModels, find a StorageModel by machineId,
        and update a specific StorageModel

    Restriction:
        - Only those with ROLES.Admin will be able to access the APIs.

    Endpoints:
        - dev/v1/storage/listall
        - dev/v1/storage/find
        - dev/v1/storage/update

    Author:
        - Lew Xu Hong (all related classes i.e. Model, Repo, Service, Request, Controller)
*/

@RestController
@RequestMapping("dev/v1/storage")
public class StorageController {

    @Autowired
    StorageService storageService;

    @Autowired
    MachineService machineService;

    @Autowired
    PeopleService peopleService;


    @GetMapping("listall")
    public ResponseEntity<?> listStorage(@RequestHeader String token) {
        try{
            PeopleModel peopleModel = peopleService.getPeopleById(peopleService.getIdByToken(token));
            if (!peopleModel.getRole().equals(PeopleModel.Role.admin)){
                return ResponseEntity.badRequest().body(new GeneralResponse("User does not have rights to acccess page"));
            }else{
                return ResponseEntity.ok(storageService.listStorage()); // keep this
            }
        }catch (Exception e){
            return ResponseEntity.badRequest().body(new GeneralResponse(e.getMessage()));
        }
    }

    @GetMapping("find")
    public ResponseEntity<?> listStorageByMachineId(@RequestBody StorageRequest storageRequest,
                                                   @RequestHeader String token )  {
        try{
            PeopleModel peopleModel = peopleService.getPeopleById(peopleService.getIdByToken(token));
            if (!peopleModel.getRole().equals(PeopleModel.Role.admin)){
                return ResponseEntity.badRequest().body(new GeneralResponse("User does not have rights to acccess page"));
            }else{
                Integer id = storageRequest.getMachineId();
                MachineModel machineModel = machineService.getMachineById(id);
                return ResponseEntity.ok(storageService.getStorageByMachineId(machineModel));
            }
        }catch (Exception e){
            return ResponseEntity.badRequest().body(new GeneralResponse(e.getMessage()));
        }
    }

    @PostMapping("update")
    public ResponseEntity<?> updateStorage(@RequestBody StorageRequest storageRequest, @RequestHeader String token)
            throws Exception {
        PeopleModel.Role role = peopleService.getRoleByToken(token);
        PeopleModel peopleModel = peopleService.getPeopleById(peopleService.getIdByToken(token));
        if (!peopleModel.getRole().equals(PeopleModel.Role.admin)){ //only admin can update storage
            return ResponseEntity.badRequest().body(new GeneralResponse("User is not admin"));
        }else{
            storageService.updateStorage(storageRequest);
            return ResponseEntity.ok(new GeneralResponse("Storage updated successfully."));
        }
    }
}
