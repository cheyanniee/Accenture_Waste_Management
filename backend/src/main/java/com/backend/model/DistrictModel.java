package com.backend.model;


import lombok.*;

import javax.persistence.*;

/*
Purpose:
    - Create data model to match with data in district table in DB.

Author:
    - Liu Fang
 */
@Entity
@Table(name="district")
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DistrictModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;

    String postalSector;
    Integer districtNum;
    String region;
}
