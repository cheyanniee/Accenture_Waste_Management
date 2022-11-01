package com.backend.request;

import lombok.*;

import java.time.ZonedDateTime;

/*
    Purpose:
        - Create class to carry data as input for Battery controller or services

    Author:
        - Liu Fang
*/

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BatteryRequest {

    Integer id;
    String type;
    Float recyclePoint;
    Float exchangePoint;
    ZonedDateTime lastUpdate;
}
