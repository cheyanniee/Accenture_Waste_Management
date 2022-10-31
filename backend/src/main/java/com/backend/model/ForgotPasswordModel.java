package com.backend.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.time.ZonedDateTime;

@Entity
@Table(name = "forgot_password")
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ForgotPasswordModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @JsonIgnore
    String encryptedOtp;
    ZonedDateTime requested_time;

    @OneToOne
    @JoinColumn(name = "people_id")
    @JsonIgnore
    PeopleModel peopleModel;

}
