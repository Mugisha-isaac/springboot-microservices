package com.programmingtechie.product_service.services.Impl;

import com.programmingtechie.product_service.DTO.ProductRequest;
import com.programmingtechie.product_service.DTO.ProductResponse;
import com.programmingtechie.product_service.model.Product;
import com.programmingtechie.product_service.repositories.IProductRepository;
import com.programmingtechie.product_service.services.IProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements IProductService {

    private final IProductRepository productRepository;
    @Override
    public void saveProduct(ProductRequest productRequest) {
        Product product = Product.builder()
                .name(productRequest.getName())
                .description(productRequest.getDescription())
                .price(productRequest.getPrice())
                .build();
        productRepository.save(product);
        log.info("Product {} saved successfully", product.getId());
    }

    @Override
    public List<ProductResponse> getAllProducts() {
        List<Product> products = productRepository.findAll();
        return products.stream().map(this::mapToProductResponse).toList();
    }

    @Override
    public ProductResponse getProductById(String id) {
        Product product = productRepository.findById(id).orElseThrow(() -> new RuntimeException("Product not found"));
        return mapToProductResponse(product);
    }

    private ProductResponse mapToProductResponse(Product product) {
        return ProductResponse.builder()
                .id(product.getId())
                .name(product.getName())
                .description(product.getDescription())
                .price(product.getPrice())
                .build();
    }
}
