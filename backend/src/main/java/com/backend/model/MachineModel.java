package com.backend.model;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "machine")
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MachineModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;

    String name;
    Float currentLoad;
    Float capacity;
    String status;

    @OneToOne
    @JoinColumn(name = "location_id")
    LocationModel locationModel;
}
