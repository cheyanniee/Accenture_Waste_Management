package com.backend.request;

import com.backend.model.PeopleModel;
import lombok.*;

/*
    Purpose:
        - Create class to carry data as input for People controller or services

    Author:
        - Liu Fang
*/

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PeopleRequest {

    //to create locationModel
    String address;
    String postcode;
    String unitNumber;

    //for peopleModel
    String firstName;
    String lastName;
    String email;
    String password;
    Integer phoneNumber;
    String dateOfBirth;
    PeopleModel.Role role;
    String officialId;

}
