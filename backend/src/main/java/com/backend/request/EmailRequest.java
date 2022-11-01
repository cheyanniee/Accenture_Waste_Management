package com.backend.request;

import lombok.*;

/*
    Purpose:
        - Create class to carry data as input for Email controller or services

    Author:
        - Liu Fang
*/

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

