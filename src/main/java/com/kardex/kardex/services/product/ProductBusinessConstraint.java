package com.kardex.kardex.services.product;

import com.kardex.kardex.entity.Product;
import com.kardex.kardex.exception.KardexError;
import org.springframework.http.HttpStatus;

import java.util.Objects;

import static com.kardex.kardex.services.product.ProductServiceImp.INTERNAL_SERVER_ERROR;

public class ProductBusinessConstraint {

    public static final String PRODUCT_ALREADY_EXISTS = "Este producto ya existe";
    public static final String PRODUCT_NOT_FOUND = "Este producto no se encuentra registrado";
    public static final String CAN_NOT_REDUCE_PRODUCT = "No se puede reducir el stock, ya que no hay unidades disponibles";
    public static final String REDUCE_ERROR = "No se puede reducir el stock, ya se supera la cantidad permitida";

    private ProductBusinessConstraint() {
    }

    public static FunctionalRunTimeExceptionRules<Product> notNull() {
        return product -> {
            if (Objects.isNull(product)) {
                throw KardexError.builder()
                        .status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .error(INTERNAL_SERVER_ERROR).build();
            }
            return product;
        };
    }

    public static FunctionalRunTimeExceptionRules<Product> validStock() {
        return product -> {
            if (product.getStockQuantity() == null || product.getStockQuantity() <= 0) {
                throw KardexError.builder()
                        .status(HttpStatus.BAD_REQUEST)
                        .error(CAN_NOT_REDUCE_PRODUCT)
                        .build();
            }
            return product;
        };
    }

    public static FunctionalRunTimeExceptionRules<Product> canReduceStock(Integer quantity) {
        return product -> {
            if (product.getStockQuantity() - quantity < 0) {
                throw KardexError.builder()
                        .status(HttpStatus.BAD_REQUEST)
                        .error(REDUCE_ERROR)
                        .build();
            }
            return product;
        };
    }




}
