package com.darm.ifce.achadoseperdidos.exceptions.handler;


import lombok.*;
import org.springframework.http.ProblemDetail;



@Getter @Setter
@AllArgsConstructor
public class ValidationExceptionDetails extends ProblemDetail {
    private final String fields;
    private final String fieldsMessage;
}
