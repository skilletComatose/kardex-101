package com.kardex.kardex.services.category;

import com.kardex.kardex.dto.product.CreateProductDto;
import com.kardex.kardex.entity.Category;
import com.kardex.kardex.entity.Product;
import com.kardex.kardex.exception.KardexError;
import com.kardex.kardex.mapper.ProductMapper;
import com.kardex.kardex.repository.CategoryRepository;
import com.kardex.kardex.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;

@Service
@RequiredArgsConstructor
public class ProductServiceImp implements ProductService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final ProductMapper productMapper;

    @Override
    public List<Product> findAll() {
        return productRepository.findAll();
    }

    @Override
    public Product createProduct(CreateProductDto product) {
        return findCategoryById()
                .andThen(category -> mapToProduct(product, category))
                .andThen(this::checkIfProductNameAlreadyExits)
                .andThen(this::saveProduct)
                .apply(product.getCategoryId());
    }

    @Override
    public Product addStock(Integer productId, Integer quantity) {
        return findProductById()
                .andThen(product -> product.addStock(quantity))
                .andThen(this::updateProduct)
                .apply(productId);
    }

    @Override
    public Product reduceStock(Integer productId, Integer quantity) {
        return findProductById()
                .andThen(product -> checkIfCanReduceStock(product, quantity))
                .andThen(product -> product.reduceStock(quantity))
                .andThen(this::updateProduct)
                .apply(productId);
    }


    private Function<Integer, Category> findCategoryById() {
        return categoryId -> categoryRepository.findById(categoryId)
                .orElseThrow(() -> KardexError.builder()
                        .status(HttpStatus.BAD_REQUEST)
                        .error("No existe la categoría")
                        .build());
    }

    private Product mapToProduct(CreateProductDto createProductDto, Category category) {
        Product product = productMapper.mapToProduct(createProductDto);
        product.setCategory(category);
        return product;
    }

    private Product saveProduct(Product product) {
        return productRepository.save(product);
    }

    private Product checkIfProductNameAlreadyExits(Product product) {
        Optional<Product> productInDb = productRepository.findByName(product.getName());
        if (productInDb.isPresent()) {
            throw KardexError.builder()
                    .status(HttpStatus.CONFLICT)
                    .error("Este producto ya existe")
                    .build();
        }
        return product;
    }

    private Function<Integer, Product> findProductById() {
        return productId -> productRepository.findById(productId)
                .orElseThrow(() -> KardexError.builder()
                        .status(HttpStatus.NOT_FOUND)
                        .error("Este producto no se encuentra registrado")
                        .build());

    }

    private Product checkIfCanReduceStock(Product product, Integer quantity) {
        Integer stock = product.getStockQuantity();
        if (stock <= 0) {
            throw KardexError.builder()
                    .status(HttpStatus.CONFLICT)
                    .error("No se puede reducir el stock, ya que no hay unidades disponibles")
                    .build();
        }

        int reducedStock = stock - quantity;

        if (reducedStock < 0) {
            throw KardexError.builder()
                    .status(HttpStatus.CONFLICT)
                    .error("No se puede reducir el stock, ya se supera la cantidad permitida")
                    .build();
        }
        return product;
    }


    private Product updateProduct(Product product) {
        return productRepository.save(product);
    }


}
