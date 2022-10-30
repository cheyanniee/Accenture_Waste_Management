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
    private String recipient;
    private String msgBody;
    private String subject;
//    private String attachment;
}

