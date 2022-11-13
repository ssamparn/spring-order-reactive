package com.reactive.microservices.productservice.service;

import com.reactive.microservices.productservice.model.Product;
import com.reactive.microservices.productservice.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Range;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.publisher.Sinks;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductResponseFactory responseFactory;
    private final ProductRepository productRepository;
    private final Sinks.Many<Product> productsSink;

    public Flux<Product> getAllProducts() {
        return this.productRepository
                .findAll()
                .map(responseFactory::toModel);
    }

    public Flux<Product> getProductsInPriceRange(Integer min, Integer max) {
        return this.productRepository
                .findByPriceBetween(Range.closed(min, max))
                .map(responseFactory::toModel);
    }

    public Mono<Product> getProductById(String id) {
        return this.productRepository.findById(id)
                .map(responseFactory::toModel);
    }

    public Mono<Product> insertProduct(Mono<Product> productMono) {
        return productMono
                .map(responseFactory::toEntity)
                .flatMap(productRepository::insert)
                .map(responseFactory::toModel)
                .doOnNext(this.productsSink::tryEmitNext);
    }

    public Mono<Product> updateProduct(String id, Mono<Product> productMono) {

        return productRepository
                .findById(id)
                .flatMap(productEntity -> productMono
                        .map(responseFactory::toEntity)
                        .doOnNext(entity -> entity.setId(id)))
                .flatMap(this.productRepository::save)
                .map(responseFactory::toModel);
    }

    public Mono<Void> deleteProduct(String id) {
        return this.productRepository.deleteById(id);
    }


}
