package com.kardex.kardex.exception;

import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import org.springframework.http.HttpStatus;


@Getter
public class KardexError extends RuntimeException   {
    private final @NonNull HttpStatus status;
    private final String error;
    @Builder
    public KardexError(HttpStatus status,  String error) {
        super(error);
        this.status = status;
        this.error = error;
    }
}
