package com.kardex.kardex.dto.product;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record CreateProductDto(
        @NotEmpty(message = "Este campo es requerido")
        String name,

        String description,

        @NotNull(message = "Este campo es requerido")
        Integer categoryId,

        @NotNull(message = "Este campo es requerido")
        @Min(value = 0, message = "Debe tener un valor positivo")
        BigDecimal price,

        String imageUrl,

        @NotNull(message = "Este campo es requerido")
        @Min(value = 1, message = "debe tener por lo menos una unidad en stock")
        Integer stockQuantity
) {
}
