package com.backend.response;

import lombok.*;


/*
Purpose:
    - Create a class as a General Response used with ResponseEntity

Author:
    - Liu Fang
 */

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GeneralResponse {
    String message;
}
