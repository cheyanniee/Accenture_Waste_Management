package com.backend.controller;


import com.backend.configuration.CustomException;
import com.backend.model.MachineModel;
import com.backend.model.PeopleModel;
import com.backend.model.StorageModel;
import com.backend.repo.StorageRepo;
import com.backend.request.StorageRequest;
import com.backend.response.GeneralResponse;
import com.backend.service.MachineService;
import com.backend.service.PeopleService;
import com.backend.service.StorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("dev/v1/storage")
public class StorageController {

    @Autowired
    StorageService storageService;

    @Autowired
    StorageRepo storageRepo;

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
    public ResponseEntity<?> listBalanceByPeopleId(@RequestBody StorageRequest storageRequest,
                                                   @RequestHeader String token )  {
        try{
            Integer id = storageRequest.getMachineId();
            MachineModel machineModel = machineService.getMachineById(id);
            return ResponseEntity.ok(storageService.getStorageByMachineId(machineModel));
        }catch (Exception e){
            return ResponseEntity.badRequest().body(new GeneralResponse(e.getMessage()));
        }
    }

    @GetMapping("test")
    public void checking() throws Exception {
        MachineModel machineModel = machineService.getMachineById(2);
        StorageModel storageModel = storageService.getStorageByMachineId(machineModel);
        storageModel.setQtyAA(storageModel.getQtyAA() - 1);
        storageRepo.save(storageModel);
    }


}
