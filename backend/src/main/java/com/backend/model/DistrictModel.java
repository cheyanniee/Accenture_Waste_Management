package com.backend.model;


import lombok.*;

import javax.persistence.*;

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
}
