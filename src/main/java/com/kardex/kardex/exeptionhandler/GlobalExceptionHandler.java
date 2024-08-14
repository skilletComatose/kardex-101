package com.kardex.kardex.exeptionhandler;

import com.kardex.kardex.exception.KardexError;
import lombok.extern.log4j.Log4j;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Map;
import java.util.Optional;

@ControllerAdvice
@Log4j2
public class GlobalExceptionHandler {

    @ExceptionHandler(KardexError.class)
    public ResponseEntity<Object> handleKardexError(KardexError error) {
        return ResponseEntity.status(error.getStatus())
                .body(Map.of(
                        "status", error.getStatus(),
                        "error", Optional.ofNullable(error.getError()).orElse("")
                ));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> genericException(Exception error) {
        log.error(error.getMessage());
        log.error(error);
        return ResponseEntity.internalServerError()
                .body(Map.of(
                        "status", 500,
                        "error", "Error en el servicio"
                ));
    }

}
