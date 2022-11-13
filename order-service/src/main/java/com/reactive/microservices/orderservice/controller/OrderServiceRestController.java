package com.reactive.microservices.orderservice.controller;

import com.reactive.microservices.orderservice.model.order.OrderPurchaseRequest;
import com.reactive.microservices.orderservice.model.order.OrderPurchaseResponse;
import com.reactive.microservices.orderservice.service.OrderFulfillmentService;
import com.reactive.microservices.orderservice.service.OrderQueryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClientRequestException;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/order")
@RequiredArgsConstructor
public class OrderServiceRestController {

    private final OrderFulfillmentService orderFulfillmentService;
    private final OrderQueryService orderQueryService;

    @PostMapping("/")
    public Mono<ResponseEntity<OrderPurchaseResponse>> createOrder(@RequestBody Mono<OrderPurchaseRequest> orderPurchaseRequestMono) {
        return this.orderFulfillmentService.processOrder(orderPurchaseRequestMono)
                .map(ResponseEntity::ok)
                .onErrorReturn(WebClientResponseException.class, ResponseEntity.badRequest().build())
                .onErrorReturn(WebClientRequestException.class, ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).build());
    }

    @GetMapping("/user/{userId}")
    public Flux<OrderPurchaseResponse> getOrderByUser(@PathVariable(value = "userId") Integer userId) {
        return orderQueryService.getProductsByUserId(userId);
    }

    @GetMapping("/users/products")
    public Flux<OrderPurchaseResponse> getAllUserProducts() {
        return orderQueryService.getAllUsersProducts();
    }
}
