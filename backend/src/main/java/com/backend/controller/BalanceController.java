package com.backend.controller;

import com.backend.configuration.CustomException;
import com.backend.model.PeopleModel;
import com.backend.request.BalanceRequest;
import com.backend.request.PeopleRequest;
import com.backend.response.GeneralResponse;
import com.backend.service.BalanceService;
import com.backend.service.PeopleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("dev/v1/balance")
public class BalanceController {

    @Autowired
    BalanceService balanceService;

    @Autowired
    PeopleService peopleService;

    @GetMapping("listall")
    public ResponseEntity<?> listBalance(@RequestHeader String token) {
        try{
            PeopleModel peopleModel = peopleService.getPeopleById(peopleService.getIdByToken(token));
            if (!peopleModel.getRole().equals(PeopleModel.Role.admin)){
                return ResponseEntity.badRequest().body(new GeneralResponse("User does not have rights to acccess page"));
            }else{
                return ResponseEntity.ok(balanceService.listBalance()); // keep this
            }
        }catch (Exception e){
            return ResponseEntity.badRequest().body(new GeneralResponse(e.getMessage()));
        }
    }

    @GetMapping("find")
    public ResponseEntity<?> listBalanceByPeopleId(@RequestHeader String token)  {
        try{
            Long id = peopleService.getIdByToken(token);
            PeopleModel peopleModel = peopleService.getPeopleById(id);
            return ResponseEntity.ok(balanceService.getBalanceByPeopleId(peopleModel));
        }catch (Exception e){
            return ResponseEntity.badRequest().body(new GeneralResponse(e.getMessage()));
        }
    }



}
