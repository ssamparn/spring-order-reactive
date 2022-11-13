package com.reactive.microservices.orderservice.model.order;

import com.reactive.microservices.orderservice.model.product.Product;
import com.reactive.microservices.orderservice.model.user.TransactionRequest;
import com.reactive.microservices.orderservice.model.user.TransactionResponse;
import lombok.Data;

@Data
public class OrderContext {
    private OrderPurchaseRequest orderPurchaseRequest;
    private Product product;
    private TransactionRequest transactionRequest;
    private TransactionResponse transactionResponse;

    public OrderContext(OrderPurchaseRequest orderPurchaseRequest) {
        this.orderPurchaseRequest = orderPurchaseRequest;
    }
}
