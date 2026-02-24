package com.grq.product_ms.rest.dtos.responses.error;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;

@AllArgsConstructor
@Getter
@Setter
public class ErrorResponse {
    private final String code;
    private final String message;
    private final Instant timestamp = Instant.now();

}

