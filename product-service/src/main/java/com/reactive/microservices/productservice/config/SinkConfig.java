package com.reactive.microservices.productservice.config;

import com.reactive.microservices.productservice.model.Product;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Sinks;

@Configuration
public class SinkConfig {

    @Bean
    public Sinks.Many<Product> sink() {
        return Sinks.many().replay().limit(1);
    }

    @Bean
    public Flux<Product> productBroadcast(Sinks.Many<Product> sink) {
        return sink.asFlux();
    }
}
