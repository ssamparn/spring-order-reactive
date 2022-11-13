package com.reactive.microservices.productservice.repository;

import com.reactive.microservices.productservice.entity.ProductEntity;
import org.springframework.data.domain.Range;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

@Repository
public interface ProductRepository extends ReactiveMongoRepository<ProductEntity, String> {
    Flux<ProductEntity> findByPriceBetween(Range<Integer> range);
}
