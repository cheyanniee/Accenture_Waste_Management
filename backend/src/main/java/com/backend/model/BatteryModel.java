package com.backend.model;

import lombok.*;

import javax.persistence.*;
import java.time.ZonedDateTime;

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
    Float recyclePoint;
    Float exchangePoint;
    ZonedDateTime lastUpdate;

}
