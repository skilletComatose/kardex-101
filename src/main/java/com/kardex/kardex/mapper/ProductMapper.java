package com.kardex.kardex.mapper;

import com.kardex.kardex.dto.product.CreateProductDto;
import com.kardex.kardex.entity.Product;

public interface ProductMapper {

    Product mapToProduct(CreateProductDto product);
}
