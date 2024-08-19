package com.reactive.microservices.productservice.controller;

import com.reactive.microservices.productservice.model.Product;
import com.reactive.microservices.productservice.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Comparator;

@RestController
@RequiredArgsConstructor
@RequestMapping("/product")
public class ProductRestController {

    private final ProductService productService;

    @GetMapping("/get/all")
    public Flux<Product> getAllProducts() {
        return this.productService.getAllProducts()
                .sort(Comparator.comparing(id -> Integer.parseInt(id.getId()))); // sorting products based on product id.
    }

    @GetMapping("/get/{id}")
    public Mono<ResponseEntity<Product>> getProductById(@PathVariable(value = "id") String id) {
        return this.productService.getProductById(id)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @GetMapping("/price-range")
    public Flux<Product> getProductsWithinPriceRange(@RequestParam(value = "min") Integer min,
                                                     @RequestParam(value = "max") Integer max) {
        return this.productService.getProductsInPriceRange(min, max);
    }

    @PostMapping("/create")
    public Mono<Product> createProduct(@RequestBody Mono<Product> product) {
        return this.productService.insertProduct(product);
    }

    @PutMapping("/update/{id}")
    public Mono<ResponseEntity<Product>> updateProduct(@PathVariable(value = "id") String id, @RequestBody Mono<Product> productMono) {
        return this.productService.updateProduct(id, productMono)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public Mono<Void> deleteProduct(@PathVariable(value = "id") String id) {
        return this.productService.deleteProduct(id);
    }
}
