package com.kardex.kardex.dto.product;

import jakarta.persistence.Column;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Builder
@Getter
@Setter
public class CreateProductDto {

    @NotEmpty(message = "Este campo es requerido")
    private String name;

    private String description;

    @NotNull(message = "Este campo es requerido")
    private Integer categoryId;

    @NotNull(message = "Este campo es requerido")
    @Column(nullable = false)
    @Min(value = 0, message = "Debe tener un valor positivo")
    private BigDecimal price;

    private String imageUrl;

    @NotNull(message = "Este campo es requerido")
    @Min(value = 1, message = "debe tener por lo menos una unidad en stock")
    private Integer stockQuantity;
}
