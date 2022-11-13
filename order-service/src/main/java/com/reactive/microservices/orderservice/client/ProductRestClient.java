package com.reactive.microservices.orderservice.client;

import com.reactive.microservices.orderservice.model.product.Product;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
public class ProductRestClient {

    private final WebClient productClient;

    public ProductRestClient(@Value("${service.url.product}") String url) {
        this.productClient = WebClient.builder()
                .baseUrl(url)
                .build();
    }

    public Mono<Product> getProduct(final String productId) {
        return this.productClient
                .get()
                .uri("/get/{id}", productId)
                .retrieve()
                .bodyToMono(Product.class);
    }

    public Flux<Product> getAllProducts() {
        return this.productClient
                .get()
                .uri("/get/all")
                .retrieve()
                .bodyToFlux(Product.class);
    }
}
