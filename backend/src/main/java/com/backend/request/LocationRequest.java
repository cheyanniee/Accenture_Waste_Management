package com.backend.request;

import lombok.*;

/*
    Purpose:
        - Create class to carry data as input for Location controller or services

    Author:
        - Liu Fang
*/


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LocationRequest {
    String address;
    String postcode;

}
