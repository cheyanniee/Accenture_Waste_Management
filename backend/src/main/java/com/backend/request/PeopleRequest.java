package com.backend.request;

import com.backend.model.LocationModel;
import com.backend.model.PeopleModel;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PeopleRequest {
    Long id;
    LocationModel locationModel; //check this one
    String firstName;
    String lastName;
    String email;
    String password;
    Integer phoneNumber;
    String dateOfBirth;
    PeopleModel.Role role;
    String officialId;


}
