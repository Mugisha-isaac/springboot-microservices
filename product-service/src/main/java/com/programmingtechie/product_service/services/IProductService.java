package com.programmingtechie.product_service.services;

import com.programmingtechie.product_service.DTO.ProductRequest;
import com.programmingtechie.product_service.DTO.ProductResponse;
import com.programmingtechie.product_service.model.Product;

import java.util.List;

public interface IProductService {
    void saveProduct(ProductRequest productRequest);

    List<ProductResponse> getAllProducts();
    ProductResponse getProductById(String id);
}
