package com.grq.product_ms.exceptions.handler;

import com.grq.product_ms.exceptions.ProducerException;
import com.grq.product_ms.rest.dtos.responses.error.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ProducerException.class)
    public ResponseEntity<ErrorResponse> handleProducerException(ProducerException ex) {
        ErrorResponse error = new ErrorResponse(
                "KAFKA_PRODUCER_ERROR",
                ex.getMessage()
        );
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
    }
}
