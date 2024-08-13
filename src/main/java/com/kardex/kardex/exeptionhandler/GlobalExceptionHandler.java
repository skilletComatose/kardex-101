package com.kardex.kardex.exeptionhandler;

import com.kardex.kardex.exception.KardexError;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Map;
import java.util.Optional;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(KardexError.class)
    public ResponseEntity<Object> handleKardexError(KardexError error) {
        return ResponseEntity.status(error.getStatus())
                .body(Map.of(
                        "status", error.getStatus(),
                        "error", Optional.ofNullable(error.getError()).orElse("")
                ));
    }
}
