package com.backend.request;

import lombok.*;

@Getter
@Setter
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EmailRequest {
    // Class data members
    String recipient;
    String msgBody;
    String subject;
//    private String attachment;
}

