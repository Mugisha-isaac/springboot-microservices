package com.programmingtechie.product_service.controller;

import com.programmingtechie.product_service.DTO.ProductRequest;
import com.programmingtechie.product_service.DTO.ProductResponse;
import com.programmingtechie.product_service.model.Product;
import com.programmingtechie.product_service.services.IProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
public class ProductController {
    private final IProductService productService;
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void saveProduct(@RequestBody ProductRequest productRequest){
        productService.saveProduct(productRequest);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<ProductResponse> getAllProducts(){
      return productService.getAllProducts();
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ProductResponse getProductById(@PathVariable String id){
        return productService.getProductById(id);
    }
}
