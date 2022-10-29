package com.backend.controller;

import com.backend.configuration.CustomException;
import com.backend.model.PeopleModel;
import com.backend.request.PeopleRequest;
import com.backend.response.GeneralResponse;
import com.backend.service.PeopleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.persistence.criteria.CriteriaBuilder;
import java.lang.reflect.Array;
import java.util.*;

@RestController
@RequestMapping("dev/v1/people")
public class PeopleController {

//    public HashMap <String, List<String>> district = new HashMap<>();
//    public HashMap <String, List<String>> region = new HashMap<>();
//    public void createLists(){
//
//        //one table - 80 rows, pk = 01 postCode
//        ArrayList<String> D1 = new ArrayList<String>(Arrays.asList("01", "02", "03", "04", "05", "06"));
//        ArrayList<String> D2 = new ArrayList<String>(Arrays.asList("07","08"));
//        ArrayList<String> D3 = new ArrayList<String>(Arrays.asList("14","15", "16"));
//        ArrayList<String> D4 = new ArrayList<String>(Arrays.asList("09", "10"));
//        ArrayList<String> D5 = new ArrayList<String>(Arrays.asList("11", "12", "13"));
//        ArrayList<String> D6 = new ArrayList<String>(Arrays.asList("17"));
//        ArrayList<String> D7 = new ArrayList<String>(Arrays.asList("18", "19"));
//        ArrayList<String> D8 = new ArrayList<String>(Arrays.asList("20", "21"));
//        ArrayList<String> D9 = new ArrayList<String>(Arrays.asList("22", "23"));
//        ArrayList<String> D10 = new ArrayList<String>(Arrays.asList("24","25","26","27"));
//        ArrayList<String> D11 = new ArrayList<String>(Arrays.asList("28", "29", "30"));
//        ArrayList<String> D12 = new ArrayList<String>(Arrays.asList("31", "32", "33"));
//        ArrayList<String> D13 = new ArrayList<String>(Arrays.asList("34", "35", "36", "37"));
//        ArrayList<String> D14 = new ArrayList<String>(Arrays.asList("38", "39", "40", "41"));
//        ArrayList<String> D15 = new ArrayList<String>(Arrays.asList("42", "43", "44", "45"));
//        ArrayList<String> D16 = new ArrayList<String>(Arrays.asList("46", "47", "48"));
//        ArrayList<String> D17 = new ArrayList<String>(Arrays.asList("49", "50", "81"));
//        ArrayList<String> D18 = new ArrayList<String>(Arrays.asList("51", "52"));
//        ArrayList<String> D19 = new ArrayList<String>(Arrays.asList("53", "54", "55", "82"));
//        ArrayList<String> D20 = new ArrayList<String>(Arrays.asList("56", "57"));
//        ArrayList<String> D21 = new ArrayList<String>(Arrays.asList("58", "59"));
//        ArrayList<String> D22 = new ArrayList<String>(Arrays.asList("60", "61", "62", "63", "64"));
//        ArrayList<String> D23 = new ArrayList<String>(Arrays.asList("65", "66", "67", "68"));
//        ArrayList<String> D24 = new ArrayList<String>(Arrays.asList("69", "70", "71"));
//        ArrayList<String> D25 = new ArrayList<String>(Arrays.asList("72", "73"));
//        ArrayList<String> D26 = new ArrayList<String>(Arrays.asList("77", "78"));
//        ArrayList<String> D27 = new ArrayList<String>(Arrays.asList("75", "76"));
//        ArrayList<String> D28 = new ArrayList<String>(Arrays.asList("79", "80"));
//
//        this.district.put("1", D1);
//        this.district.put("2", D2);
//        this.district.put("3", D3);
//        this.district.put("4", D4);
//        this.district.put("5", D5);
//        this.district.put("6", D6);
//        this.district.put("7", D7);
//        this.district.put("8", D8);
//        this.district.put("9", D9);
//        this.district.put("10", D10);
//        this.district.put("11", D11);
//        this.district.put("12", D12);
//        this.district.put("13", D13);
//        this.district.put("14", D14);
//        this.district.put("15", D15);
//        this.district.put("16", D16);
//        this.district.put("17", D17);
//        this.district.put("18", D18);
//        this.district.put("19", D19);
//        this.district.put("20", D20);
//        this.district.put("21", D21);
//        this.district.put("22", D22);
//        this.district.put("23", D23);
//        this.district.put("24", D24);
//        this.district.put("25", D25);
//        this.district.put("26", D26);
//        this.district.put("27", D27);
//        this.district.put("28", D28);
//
//        //another table
//        ArrayList<String> Central = new ArrayList<String>(Arrays.asList
//                ("1", "2", "3", "4", "6", "7", "8", "9", "10", "11", "12", "13", "14", "21", "26"));
//        ArrayList<String> NorthEast = new ArrayList<String>(Arrays.asList
//                ("19", "20", "28"));
//        ArrayList<String> North = new ArrayList<String>(Arrays.asList
//                ("24", "25", "27"));
//        ArrayList<String> East = new ArrayList<String>(Arrays.asList
//                ("15", "16", "17", "18" ));
//        ArrayList<String> West = new ArrayList<String>(Arrays.asList
//                ("5", "22", "23"));
//
//        this.region.put("Central", Central);
//        this.region.put("NorthEast", NorthEast);
//        this.region.put("North", North);
//        this.region.put("East", East);
//        this.region.put("West", West);
//    }


    @Autowired
    PeopleService peopleService;

    @GetMapping("listall")
    public ResponseEntity<?> listPeople() {
        return ResponseEntity.ok(peopleService.listPeople());
    }

    @PostMapping("register")
    public ResponseEntity<?> registerPeople(@RequestBody PeopleRequest peopleRequest) {
        try {
            peopleService.createUser(peopleRequest);
            return ResponseEntity.ok(new GeneralResponse("Register successfully!"));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new GeneralResponse(e.getMessage()));
        }
    }

    @PostMapping("register/collector")
    public ResponseEntity<?> registerCollector(@RequestBody PeopleRequest peopleRequest, @RequestHeader String token) {
        //verify if requester is an admin
        Long id = peopleService.getIdByToken(token);
        try{
            PeopleModel peopleModel = peopleService.getPeopleById(id);
            if (!peopleModel.getRole().equals(PeopleModel.Role.admin)){
                return ResponseEntity.badRequest().body(new GeneralResponse("User is not admin"));
            }else{
                peopleService.createCollector(peopleRequest);
                return ResponseEntity.ok(new GeneralResponse("Register collector successfully!"));
            }
        }catch (Exception e){
            return ResponseEntity.badRequest().body(new GeneralResponse(e.getMessage()));
        }
    }

    @PostMapping("register/admin")
    public ResponseEntity<?> registerAdmin(@RequestBody PeopleRequest peopleRequest, @RequestHeader String token) {
        //verify if requester is an admin
        Long id = peopleService.getIdByToken(token);
        try{
            PeopleModel peopleModel = peopleService.getPeopleById(id);
            if (!peopleModel.getRole().equals(PeopleModel.Role.admin)){
                return ResponseEntity.badRequest().body(new GeneralResponse("User is not admin"));
            }else{
                peopleService.createAdmin(peopleRequest);
                return ResponseEntity.ok(new GeneralResponse("Register admin successfully!"));
            }
        }catch (Exception e){
            return ResponseEntity.badRequest().body(new GeneralResponse(e.getMessage()));
        }

    }

    @PostMapping("login")
    public ResponseEntity<?> login(@RequestBody PeopleRequest peopleRequest) {
        try {
            return ResponseEntity.ok(peopleService.loginValidate(peopleRequest.getEmail(), peopleRequest.getPassword()));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new GeneralResponse(e.getMessage()));
        }
    }

    @GetMapping("getinfo")
    public ResponseEntity<?> getPeople(@RequestHeader String token) throws Exception {
        PeopleModel people = peopleService.getPeopleById(peopleService.getIdByToken(token));
        return ResponseEntity.ok(people);
    }

    @PostMapping("update")
    public ResponseEntity<?> updatePeople(@RequestBody PeopleRequest peopleRequest, @RequestHeader String token) throws CustomException {
        peopleService.updatePeople(peopleRequest, token);
        return ResponseEntity.ok(new GeneralResponse("User " + peopleRequest.getFirstName() + " " + peopleRequest.getLastName() + " update successfully!"));

    }

    @GetMapping("logout")
    public ResponseEntity<?> logout(@RequestHeader String token) throws Exception {
        peopleService.logout(peopleService.getIdByToken(token));
        return ResponseEntity.ok(new GeneralResponse("Logout successfully!"));
    }

    @GetMapping("find")
    public ResponseEntity<?> findPeopleByOfficialId(@RequestParam String officialId) throws CustomException {
        return ResponseEntity.ok(peopleService.findPeopleByOfficialId(officialId));
    }

    //people collector endpoint

}
