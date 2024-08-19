package com.reactive.microservices.productservice.service;

import com.reactive.microservices.productservice.model.Product;
import com.reactive.microservices.productservice.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;

@Service
@RequiredArgsConstructor
public class DataInitializationService implements CommandLineRunner {

    private final ProductResponseFactory responseFactory;
    private final ProductRepository productRepository;

    @Override
    public void run(String... args) {
        Product p1 = Product.create("1", "Study Table", 150);
        Product p2 = Product.create("2", "Gaming Chair", 250);
        Product p3 = Product.create("3", "King Size Bed", 350);
        Product p4 = Product.create("4", "Bed Lamp", 450);
        Product p5 = Product.create("5", "Fiction Book", 550);
        Product p6 = Product.create("6", "Gel Pen", 650);
        Product p7 = Product.create("7", "Bensia Pencil", 750);
        Product p8 = Product.create("8", "Sony Television", 850);
        Product p9 = Product.create("9", "Dining table", 950);
        Product p10 = Product.create("10", "Kitchen Glasses", 1050);

        productRepository.deleteAll()
                .publishOn(Schedulers.boundedElastic()) // When events starts flowing from top to bottom, the moment it encounters publishOn(),
                // events offloads to the bounded elastic thread pool and bounded elastic thread pool will save events to the database
                .thenMany(Flux.just(p1, p2, p3, p4, p5, p6, p7, p8, p9, p10))
                .map(responseFactory::toEntity)
                .flatMap(productRepository::save)
                .subscribe();
    }
}
