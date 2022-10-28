package com.backend.model;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "battery")
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BatteryModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;

    String type;
    Float valuePerWeight;
    LocalDate lastUpdate;

}
