package com.backend.model;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

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

    LocalDateTime assignedTime;
    LocalDateTime collectedTime;
    LocalDateTime deliveredTime;

    @OneToOne
    @JoinColumn(name = "people_id")
    PeopleModel peopleModel;

    @OneToOne
    @JoinColumn(name = "machine_id")
    MachineModel machineModel;
}
