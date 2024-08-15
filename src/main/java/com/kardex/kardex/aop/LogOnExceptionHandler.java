package com.kardex.kardex.aop;

import com.kardex.kardex.exception.KardexError;
import lombok.extern.log4j.Log4j2;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Aspect
@Component
@Log4j2
public class LogOnExceptionHandler {

    private static <T extends Throwable> void logException(JoinPoint joinPoint, Class<T> errorClass) {
        Arrays.stream(joinPoint.getArgs())
                .filter(errorClass::isInstance)
                .findFirst()
                .map(errorClass::cast)
                .ifPresent(log::error);
    }

    @Before("execution(* *(..)) && @annotation(org.springframework.web.bind.annotation.ExceptionHandler)")
    public void handleValidations(JoinPoint joinPoint) {
        logException(joinPoint, KardexError.class);
        logException(joinPoint, Exception.class);
    }


}
