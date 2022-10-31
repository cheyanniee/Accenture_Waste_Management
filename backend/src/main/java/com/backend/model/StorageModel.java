package com.backend.model;

import lombok.*;

import javax.persistence.*;

/*
    Purpose:
        - Entity to match with table in database (Storage)

    Author:
        - Lew Xu Hong
*/
@Entity
@Table(name="storage")
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class StorageModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;

    //foreign key with machine
    @OneToOne
    @JoinColumn(name="machine_id")
    MachineModel machineModel;

    @Column(name="qty_aa")
    Integer qtyAA;

    @Column(name="qty_aaa")
    Integer qtyAAA;
}
