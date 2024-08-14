package com.kardex.kardex.services.category;

import com.kardex.kardex.entity.Category;
import com.kardex.kardex.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class DefaultCategoryService implements CategoryService{
    private final CategoryRepository repository;
    @Override
    public List<Category> getAll() {
        return repository.findAll();
    }
}
