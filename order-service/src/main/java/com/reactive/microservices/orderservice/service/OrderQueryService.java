package com.reactive.microservices.orderservice.service;

import com.reactive.microservices.orderservice.client.ProductRestClient;
import com.reactive.microservices.orderservice.client.UserRestClient;
import com.reactive.microservices.orderservice.model.order.OrderPurchaseRequest;
import com.reactive.microservices.orderservice.model.order.OrderPurchaseResponse;
import com.reactive.microservices.orderservice.model.product.Product;
import com.reactive.microservices.orderservice.model.user.User;
import com.reactive.microservices.orderservice.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

@Service
@RequiredArgsConstructor
public class OrderQueryService {

    private final OrderServiceResponseFactory responseFactory;
    private final OrderRepository orderRepository;
    private final UserRestClient userRestClient;
    private final ProductRestClient productRestClient;
    private final OrderFulfillmentService orderFulfillmentService;

    public Flux<OrderPurchaseResponse> getProductsByUserId(Integer id) {
        return Flux.fromStream(orderRepository.findByUserId(id)
                .stream()
                .map(responseFactory::toModel))
                .subscribeOn(Schedulers.boundedElastic());
    }

    public Flux<OrderPurchaseResponse> getAllUsersProducts() {
        return Flux.zip(userRestClient.getAllUsers(), productRestClient.getAllProducts())
                .map(t -> toModel(t.getT1(), t.getT2()))
                .flatMap(request -> orderFulfillmentService.processOrder(Mono.just(request)));
    }

    private OrderPurchaseRequest toModel(User user, Product product) {
        OrderPurchaseRequest request = new OrderPurchaseRequest();
        request.setUserId(user.getId());
        request.setProductId(Integer.parseInt(product.getId()));

        return request;
    }
}
