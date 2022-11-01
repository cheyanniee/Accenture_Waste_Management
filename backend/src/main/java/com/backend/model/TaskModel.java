package com.backend.model;

import lombok.*;

import javax.persistence.*;
import java.time.ZonedDateTime;

/*
Purpose:
    - Create data model to match with task table in DB.

Author:
    - Alex
 */

@Entity
@Table(name = "task")
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TaskModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    ZonedDateTime assignedTime;
    ZonedDateTime collectedTime;
    ZonedDateTime deliveredTime;

    @OneToOne
    @JoinColumn(name = "collectorId")
    PeopleModel collector;

    @OneToOne
    @JoinColumn(name = "assignedByAdminId")
    PeopleModel admin;

    @OneToOne
    @JoinColumn(name = "machineId")
    MachineModel machine;
}
