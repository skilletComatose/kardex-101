package com.kardex.kardex.services.category;

import com.kardex.kardex.dto.product.CreateProductDto;
import com.kardex.kardex.entity.Product;

import java.util.List;

public interface ProductService {

    List<Product> findAll();

    Product createProduct(CreateProductDto product);

    Product addStock(Integer productId, Integer quantity);
    Product reduceStock(Integer productId, Integer quantity);

}
