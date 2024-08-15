package com.kardex.kardex.mapper;

import com.kardex.kardex.dto.product.CreateProductDto;
import com.kardex.kardex.entity.Category;
import com.kardex.kardex.entity.Product;
import org.springframework.stereotype.Component;

@Component
public class DefaultProductMapper implements ProductMapper {
    @Override
    public Product mapToProduct(CreateProductDto product) {
        return Product.builder()
                .name(product.name())
                .description(product.description())
                .price(product.price())
                .stockQuantity(product.stockQuantity())
                .imageUrl(product.imageUrl())
                .category(Category.builder().categoryId(product.categoryId()).build())
                .build();
    }
}
