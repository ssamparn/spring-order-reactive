package com.reactive.microservices.orderservice.service;

import com.reactive.microservices.orderservice.client.ProductRestClient;
import com.reactive.microservices.orderservice.client.UserRestClient;
import com.reactive.microservices.orderservice.model.order.OrderPurchaseRequest;
import com.reactive.microservices.orderservice.model.order.OrderPurchaseResponse;
import com.reactive.microservices.orderservice.model.order.OrderContext;
import com.reactive.microservices.orderservice.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;
import reactor.util.retry.Retry;

import java.time.Duration;

@Service
@RequiredArgsConstructor
public class OrderFulfillmentService {

    private final OrderServiceResponseFactory responseFactory;
    private final ProductRestClient productRestClient;
    private final UserRestClient userRestClient;
    private final OrderRepository orderRepository;

    public Mono<OrderPurchaseResponse> processOrder(Mono<OrderPurchaseRequest> requestMono) {
        return requestMono
                .map(OrderContext::new)
                .flatMap(this::productServiceResponse)
                .doOnNext(responseFactory::setTransactionRequest)
                .flatMap(this::userTransactionResponse)
                .map(responseFactory::toEntity)
                .map(orderRepository::save) // blocking
                .map(responseFactory::toModel)
                .subscribeOn(Schedulers.boundedElastic());
    }

    private Mono<OrderContext> productServiceResponse(OrderContext orderContext) {
        return this.productRestClient.getProduct(String.valueOf(orderContext.getOrderPurchaseRequest().getProductId()))
                .doOnNext(orderContext::setProduct)
                .retryWhen(Retry.fixedDelay(3, Duration.ofMillis(250)))
                .thenReturn(orderContext);
    }

    private Mono<OrderContext> userTransactionResponse(OrderContext orderContext) {
        return this.userRestClient.authorizeTransaction(orderContext.getTransactionRequest())
                .doOnNext(orderContext::setTransactionResponse)
                .thenReturn(orderContext);
    }

}
