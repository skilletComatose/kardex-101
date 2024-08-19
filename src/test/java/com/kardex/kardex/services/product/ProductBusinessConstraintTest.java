package com.kardex.kardex.services.product;

import com.kardex.kardex.entity.Product;
import org.junit.jupiter.api.Test;

class ProductBusinessConstraintTest {


    @Test
    void testValidation() {
        Product product = Product.builder()
                .stockQuantity(1)
                .build();
        ProductBusinessConstraint.notNull()
                .thenTest(ProductBusinessConstraint.validStock())
                .thenTest(ProductBusinessConstraint.canReduceStock(1))
                .test(product);
    }



}