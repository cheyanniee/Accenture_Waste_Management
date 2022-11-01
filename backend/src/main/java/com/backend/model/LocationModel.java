package com.backend.model;


import lombok.*;

import javax.persistence.*;

/*
Purpose:
    - Create data model to match with data in location table in DB.

Author:
    - Liu Fang
 */

@Entity
@Table(name = "location")
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LocationModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    String address;
    String postcode;

    @OneToOne
    @JoinColumn(name = "district_id")
    DistrictModel districtModel;

}
