package com.kardex.kardex.services.product;



import java.util.Objects;

@FunctionalInterface
public  interface FunctionalRunTimeExceptionRules<R> {

    R test(R r);

    default FunctionalRunTimeExceptionRules<R> thenTest(FunctionalRunTimeExceptionRules<R> after) {
        Objects.requireNonNull(after);
        return (R r) -> after.test(test(r));
    }

}
