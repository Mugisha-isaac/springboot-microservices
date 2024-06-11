package com.programmingtechie.product_service.repositories;

import com.programmingtechie.product_service.model.Product;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface IProductRepository extends MongoRepository<Product,String> {
}
