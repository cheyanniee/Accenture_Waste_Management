package com.backend.model;

import lombok.*;

import javax.persistence.*;
import java.time.ZonedDateTime;


/*
Purpose:
    - Create data model to match with data in battery table in DB.

Author:
    - Liu Fang
 */
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
