package com.backend.model;

import lombok.*;

import javax.persistence.*;
import java.time.ZonedDateTime;

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
    @JoinColumn(name = "people_id")
    PeopleModel peopleModel;

    @OneToOne
    @JoinColumn(name = "machine_id")
    MachineModel machineModel;
}
