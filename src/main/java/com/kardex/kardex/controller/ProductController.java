package com.kardex.kardex.controller;

import com.kardex.kardex.dto.product.CreateProductDto;
import com.kardex.kardex.entity.Product;
import com.kardex.kardex.services.product.ProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("api/v1/products")
public class ProductController {

    private final ProductService productService;

    @PostMapping(path = "")
    public ResponseEntity<Product> addNewProduct(@Valid @RequestBody CreateProductDto product) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(productService.createProduct(product));
    }

    @PutMapping(path = "/{productId}/stock/add/{quantity}")
    public ResponseEntity<Product> addStock(@PathVariable Integer productId, @PathVariable Integer quantity) {
        return ResponseEntity.ok(productService.addStock(productId, quantity));
    }

    @PutMapping(path = "/{productId}/stock/reduce/{quantity}")
    public ResponseEntity<Product> reduceStock(@PathVariable Integer productId, @PathVariable Integer quantity) {
        return ResponseEntity.ok(productService.reduceStock(productId, quantity));
    }

    @GetMapping(path = "")
    public ResponseEntity<List<Product>> getAllProducts() {
        return ResponseEntity.ok(productService.findAll());
    }
}
