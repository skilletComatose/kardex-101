package com.kardex.kardex.repository;

import com.kardex.kardex.entity.Product;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends CrudRepository<Product, Integer> {
    @Override
    List<Product> findAll();

    Optional<Product> findByName(String productName);


    Optional<Product> findById(Integer productId);

}
