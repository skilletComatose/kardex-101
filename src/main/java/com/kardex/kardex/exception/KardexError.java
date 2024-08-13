package com.kardex.kardex.exception;

import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import org.springframework.http.HttpStatus;

import java.io.Serializable;


@Getter
public class KardexError extends RuntimeException implements Serializable  {
    private final @NonNull HttpStatus status;
    private final String error;
    @Builder
    public KardexError(HttpStatus status,  String error) {
        super(error);
        this.status = status;
        this.error = error;
    }
}
