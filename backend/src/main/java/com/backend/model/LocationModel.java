package com.backend.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;

@Entity
@Table(name="location")
@Builder
//@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LocationModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    //    @Column(name = "last_name")
    String address;
    String postcode;
    String regionName;
    String areaName;
}
