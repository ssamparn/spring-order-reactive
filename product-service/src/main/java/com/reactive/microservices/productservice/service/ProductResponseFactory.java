package com.reactive.microservices.productservice.service;

import com.reactive.microservices.productservice.entity.ProductEntity;
import com.reactive.microservices.productservice.model.Product;
import org.springframework.stereotype.Service;

@Service
public class ProductResponseFactory {

    public Product toModel(ProductEntity productEntity) {
        return Product.create(
                productEntity.getId(),
                productEntity.getDescription(),
                productEntity.getPrice()
        );
    }

    public ProductEntity toEntity(Product product) {
        return ProductEntity.create(
                product.getId(),
                product.getDescription(),
                product.getPrice()
        );
    }
}
