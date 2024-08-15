package com.kardex.kardex.exceptionhandler;

import com.kardex.kardex.exception.KardexError;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.resource.NoResourceFoundException;

@ControllerAdvice
public class GlobalExceptionHandler {

    public static final String REQUEST_BODY_ERROR = "Error en la información de entrada";
    public static final String INTERNAR_SERVER_ERROR = "Error en el servicio";
    public static final String NO_RESOURCE_FOUND = "Recurso no encontrado";

    @ExceptionHandler(KardexError.class)
    public ResponseEntity<Object> handleKardexError(KardexError error) {
        return ResponseEntity.status(error.getStatus())
                .body(ErrorResponse.of(error.getStatus().value(), error.getError()));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> genericException(Exception error) {
        return ResponseEntity.internalServerError()
                .body(ErrorResponse.internalServerError(INTERNAR_SERVER_ERROR));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Object> handleMethodArgumentNotValidException(MethodArgumentNotValidException error) {
        return ResponseEntity.badRequest()
                .body(ErrorResponse.fromBodyRequestErrors(REQUEST_BODY_ERROR, error));
    }

    @ExceptionHandler(NoResourceFoundException.class)
    public ResponseEntity<Object> handleNoResourceFoundException(NoResourceFoundException error) {
        return ResponseEntity.badRequest()
                .body(ErrorResponse.noResourceFound(NO_RESOURCE_FOUND));
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<Object> handleMethodArgumentTypeMismatchException(MethodArgumentTypeMismatchException error) {
        return ResponseEntity.badRequest()
                .body(ErrorResponse.of(
                        400,
                        REQUEST_BODY_ERROR,
                        new FieldError(error.getName(), "Formato incorrecto")
                ));
    }
}
