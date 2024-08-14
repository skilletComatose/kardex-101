package com.kardex.kardex.repository;

import com.kardex.kardex.entity.Category;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryRepository extends CrudRepository<Category, Integer> {
    @Override
    List<Category> findAll();
}
