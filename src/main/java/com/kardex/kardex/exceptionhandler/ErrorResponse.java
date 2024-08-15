package com.kardex.kardex.exceptionhandler;

import com.fasterxml.jackson.annotation.JsonInclude;
import org.springframework.web.bind.MethodArgumentNotValidException;

import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record ErrorResponse<T>(int status, String error, T errorDetails) {
    public static ErrorResponse<String> of(int status, String message) {
        return new ErrorResponse<>(status, message, null);
    }

    public static <T> ErrorResponse<T> of(int status, String message, T errorDetails) {
        return new ErrorResponse<>(status, message, errorDetails);
    }

    public static ErrorResponse<String> internalServerError(String message) {
        return of(500, message);
    }

    public static ErrorResponse<String> noResourceFound(String message) {
        return of(404, message);
    }
    public static ErrorResponse<List<FieldError>> fromBodyRequestErrors(String message, MethodArgumentNotValidException validationErrors) {

        return new ErrorResponse<>(400,
                message,
                validationErrors.getFieldErrors()
                        .parallelStream()
                        .map(fieldError -> new FieldError(fieldError.getField(), fieldError.getDefaultMessage()))
                        .toList()
        );
    }

}
