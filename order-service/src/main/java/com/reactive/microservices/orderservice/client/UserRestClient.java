package com.reactive.microservices.orderservice.client;

import com.reactive.microservices.orderservice.model.user.TransactionRequest;
import com.reactive.microservices.orderservice.model.user.TransactionResponse;
import com.reactive.microservices.orderservice.model.user.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
public class UserRestClient {

    private final WebClient userClient;

    public UserRestClient(@Value("${service.url.user}") String url) {
        this.userClient = WebClient.builder()
                .baseUrl(url)
                .build();
    }

    public Mono<TransactionResponse> authorizeTransaction(final TransactionRequest request) {
        return this.userClient
                .post()
                .uri("/transactions")
                .bodyValue(request)
                .retrieve()
                .bodyToMono(TransactionResponse.class);
    }

    public Flux<User> getAllUsers() {
        return this.userClient
                .get()
                .uri("/get/all")
                .retrieve()
                .bodyToFlux(User.class);
    }
}
