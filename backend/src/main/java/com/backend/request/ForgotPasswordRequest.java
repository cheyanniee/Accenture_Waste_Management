package com.backend.request;

import lombok.*;

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
