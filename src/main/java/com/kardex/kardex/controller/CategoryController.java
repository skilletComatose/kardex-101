package com.kardex.kardex.controller;

import com.kardex.kardex.entity.Category;
import com.kardex.kardex.services.category.CategoryService;
import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("api/v1/categories")
public class CategoryController {

    private final CategoryService categoryService;

    @GetMapping(path = "")
    public ResponseEntity<List<Category>> addNewProduct() {
        return ResponseEntity.ok(categoryService.getAll());
    }
}
