package com.backend.service;

import com.backend.configuration.CustomException;
import com.backend.model.LocationModel;
import com.backend.model.PeopleModel;
import com.backend.repo.LocationRepo;
import com.backend.repo.PeopleRepo;
import com.backend.request.PeopleRequest;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.CriteriaBuilder;
import javax.xml.stream.Location;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.*;

@Service
public class PeopleService {

    @Autowired
    PeopleRepo peopleRepo;

    @Autowired
    LocationRepo locationRepo;

    @Autowired
    Environment environment;


    public List<PeopleModel> listPeople() {
        return peopleRepo.findAll();
    }

    public void createUser(PeopleRequest peopleRequest) throws Exception {

        Optional<PeopleModel> emailExist = peopleRepo.getPeopleByEmail(peopleRequest.getEmail().toLowerCase());
        if (emailExist.isPresent()) {
            throw new Exception("Email already exists.");
        }

        Optional<PeopleModel> officialIdExist = peopleRepo.getPeopleByOfficialId(peopleRequest.getOfficialId().toUpperCase());
        if(officialIdExist.isPresent()){
            throw new Exception("Official ID already exists.");
        }

        //create locationModel from address details input by user
        LocationModel locationNew = LocationModel.builder()
                .address(peopleRequest.getLocationModel().getAddress())
                .postcode(peopleRequest.getLocationModel().getPostcode())
                .regionName(peopleRequest.getLocationModel().getRegionName())
                .areaName(peopleRequest.getLocationModel().getAreaName())
                .build();
        locationRepo.save(locationNew);


        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        PeopleModel peopleNew = PeopleModel.builder()
                .firstName(peopleRequest.getFirstName())
                .lastName(peopleRequest.getLastName())
                .locationModel(locationNew) //check if this is working
                .email(peopleRequest.getEmail().toLowerCase())
                .password(passwordEncoder.encode(peopleRequest.getPassword()))
                .phoneNumber(peopleRequest.getPhoneNumber())
                .dateOfBirth(peopleRequest.getDateOfBirth())
                .role(Objects.isNull(peopleRequest.getRole()) ? PeopleModel.Role.user : peopleRequest.getRole())
                .officialId(peopleRequest.getOfficialId().toUpperCase())
                .build();

        peopleRepo.save(peopleNew);
    }

    public Jws<Claims> validateJWT(String token) {
        return Jwts.parser().setSigningKey(environment.getProperty("JWT_SECRET")).parseClaimsJws(token);
//        return true;
    }

    public boolean validateToken(String token, Long peopleId) throws Exception {
        PeopleModel user = peopleRepo.findById(peopleId).orElseThrow(
                () -> new Exception("UserID not found"));
        if (user.getToken().equals(token)) {
            return true;
        } else {
            throw new Exception("Token not match");
        }
    }

    public Long getIdByToken(String token) throws NumberFormatException {
        return Long.valueOf((String) validateJWT(token).getBody().get("jti"));
    }

    public PeopleModel.Role getRoleByToken(String token) {
        PeopleModel.Role role = PeopleModel.Role.valueOf(validateJWT(token).getBody().get("role").toString());
        return role;
    }

    public PeopleModel loginValidate(String email, String password) throws Exception {
//        Optional<PeopleModel> peopleOpt = peopleRepo.getPeopleByEmailAndPassword(email.toLowerCase(), password);
        PeopleModel people = peopleRepo.getPeopleByEmail(email.toLowerCase()).orElseThrow(()->new Exception("Please provide correct Email and Password."));
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        if (passwordEncoder.matches(password,people.getPassword())) {
//            PeopleModel people = peopleOpt.get();
//            String token = genTokenForEmail(email);
            String token = genJWT(people, 1, 0);
            updateTokenById(token, people.getId());
            people.setToken(token);
            ZoneId zid = ZoneId.of("Asia/Singapore");
            ZonedDateTime dtLogin = ZonedDateTime.now(zid);
            updateLastLoginById(dtLogin, people.getId());
            people.setLastLogin(dtLogin);

            return people;
        } else {
            throw new Exception("Please provide correct Email and Password.");
        }
    }

    private String genJWT(PeopleModel people, int hour, int min) {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.MINUTE, min);
        cal.add(Calendar.HOUR, hour);
        return Jwts.builder()
                .claim("email", people.getEmail())
                .setSubject(people.getFirstName() + "_" + people.getLastName())
                .setId(String.valueOf(people.getId()))
                .setIssuedAt(new Date())
                .setExpiration(cal.getTime())
                .claim("role",people.getRole())
                .signWith(SignatureAlgorithm.HS512, environment.getProperty("JWT_SECRET"))
                .compact();
    }

    private void updateTokenById(String token, Long peopleId) throws Exception {
        try {
            peopleRepo.updateTokenByPeopleId(token, peopleId);
        } catch (Exception e) {
            throw new Exception("Update fail");
        }
    }

    private void updateLastLoginById(ZonedDateTime dtLogin, Long peopleId) throws Exception {
        try {
            peopleRepo.updateLastLoginByPeopleId(dtLogin, peopleId);
        } catch (Exception e) {
            throw new Exception("Update fail");
        }
    }

    public PeopleModel getPeopleById(Long peopleId) throws Exception {
        return peopleRepo.findById(peopleId).orElseThrow(() -> new Exception("UserID not found"));
    }

    public boolean updatePeople(PeopleRequest peopleRequest, String token) throws CustomException {
        PeopleModel people = peopleRepo.findById(getIdByToken(token)).orElseThrow(() -> new CustomException("User is not found!"));//get the data bases on primary key

        if (peopleRequest.getFirstName() != null && !peopleRequest.getFirstName().equals("")) {
            people.setFirstName(peopleRequest.getFirstName());
        }
        if (peopleRequest.getLastName() != null && !peopleRequest.getLastName().equals("")) {
            people.setLastName(peopleRequest.getLastName());
        }
        if (peopleRequest.getEmail() != null && !peopleRequest.getEmail().equals("")) {
            people.setEmail(peopleRequest.getEmail());
        }
        if (peopleRequest.getPassword() != null && !peopleRequest.getPassword().equals("")) {
            people.setPassword(peopleRequest.getPassword());
        }
        if (peopleRequest.getPhoneNumber() != null && !peopleRequest.getPhoneNumber().equals("")) {
            people.setPhoneNumber(peopleRequest.getPhoneNumber());
        }
        if (peopleRequest.getDateOfBirth() != null && !peopleRequest.getDateOfBirth().equals("")) {
            people.setDateOfBirth(peopleRequest.getDateOfBirth());
        }
        if (peopleRequest.getOfficialId() != null && !peopleRequest.getOfficialId().equals("")) {
            people.setOfficialId(peopleRequest.getOfficialId());
        }
        peopleRepo.save(people);//update the data as it has Primary key
        return true;
    }

    public void logout(Long peopleId) throws Exception {
        updateTokenById("", peopleId);
    }

    public PeopleModel findPeopleByOfficialId(String officialId) throws CustomException {
        return peopleRepo.getPeopleByOfficialId(officialId).orElseThrow(() -> new CustomException("No user with this Official ID."));
    }
}
