package com.kardex.kardex.services.product;

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
import java.util.function.IntUnaryOperator;
import java.util.function.UnaryOperator;

@Service
@RequiredArgsConstructor
public class ProductServiceImp implements ProductService {

    public static final String CATEGORY_NOT_FOUD = "No existe la categoría";
    public static final String PRODUCT_ALREADY_EXISTS = "Este producto ya existe";
    public static final String PRODUCT_NOT_FOUND = "Este producto no se encuentra registrado";
    public static final String INTERNAL_SERVER_ERROR = "Error interno en el servicio";
    public static final String CAN_NOT_REDUCE_PRODUCT = "No se puede reducir el stock, ya que no hay unidades disponibles";
    public static final String REDUCE_ERROR = "No se puede reducir el stock, ya se supera la cantidad permitida";

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
                .andThen(mapToProduct(product))
                .andThen(checkIfProductNameAlreadyExits())
                .andThen(saveProduct())
                .apply(product.categoryId());
    }

    @Override
    public Product addStock(Integer productId, Integer quantity) {
        return findProductById()
                .andThen(stockOperation(stock -> stock + quantity))
                .andThen(updateProduct())
                .apply(productId);
    }

    @Override
    public Product reduceStock(Integer productId, Integer quantity) {
        return findProductById()
                .andThen(checkIfCanReduceStock(quantity))
                .andThen(stockOperation(stock -> stock - quantity))
                .andThen(updateProduct())
                .apply(productId);
    }


    private Function<Integer, Category> findCategoryById() {
        return categoryId -> categoryRepository.findById(categoryId)
                .orElseThrow(() -> KardexError.builder()
                        .status(HttpStatus.BAD_REQUEST)
                        .error(CATEGORY_NOT_FOUD)
                        .build());
    }

    private Function<Category, Product> mapToProduct(CreateProductDto createProductDto) {
        return category -> productMapper.mapToProduct(createProductDto)
                .toBuilder()
                .category(category)
                .build();
    }

    private UnaryOperator<Product> stockOperation(IntUnaryOperator operation) {
        return product -> product.toBuilder()
                .stockQuantity(operation.applyAsInt(product.getStockQuantity()))
                .build();
    }

    private UnaryOperator<Product> saveProduct() {
        return productRepository::save;
    }

    private UnaryOperator<Product> checkIfProductNameAlreadyExits() {
        return product -> {
            Optional<Product> p = productRepository.findByName(product.getName());
            if (p.isPresent()) {
                throw KardexError.builder().status(HttpStatus.CONFLICT)
                        .error(PRODUCT_ALREADY_EXISTS).build();
            }
            return product;
        };

    }

    private Function<Integer, Product> findProductById() {
        return productId -> productRepository.findById(productId)
                .orElseThrow(() -> KardexError.builder()
                        .status(HttpStatus.NOT_FOUND)
                        .error(PRODUCT_NOT_FOUND)
                        .build());

    }


    private UnaryOperator<Product> checkIfCanReduceStock(Integer quantity) {
        return product -> switch (product) {

            case null -> throw KardexError.builder().status(HttpStatus.INTERNAL_SERVER_ERROR).error(INTERNAL_SERVER_ERROR).build();

            case Product p2 when p2.getStockQuantity() == null || p2.getStockQuantity() <= 0
                      ->    throw KardexError.builder()
                            .status(HttpStatus.BAD_REQUEST)
                            .error(CAN_NOT_REDUCE_PRODUCT)
                            .build();

            case Product p2 when p2.getStockQuantity() - quantity < 0
                       -> throw KardexError.builder()
                            .status(HttpStatus.BAD_REQUEST)
                            .error(REDUCE_ERROR)
                            .build();

            default -> product;
        };
    }

    private UnaryOperator<Product> updateProduct() {
        return productRepository::save;
    }


}
