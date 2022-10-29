package com.backend.model;


import lombok.*;

import javax.persistence.*;

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
