package com.backend.request;

import lombok.*;

/*
    Purpose:
        - Create class to carry data as input for ForgotPassword controller or services

    Author:
        - Liu Fang
*/

@Getter
@Setter
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ForgotPasswordRequest {
    Long id;
    String email;
    String password;
    String otp;
}
