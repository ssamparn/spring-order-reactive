package com.reactive.microservices.orderservice.service;

import com.reactive.microservices.orderservice.entity.OrderEntity;
import com.reactive.microservices.orderservice.model.order.OrderContext;
import com.reactive.microservices.orderservice.model.order.OrderPurchaseResponse;
import com.reactive.microservices.orderservice.model.order.OrderStatus;
import com.reactive.microservices.orderservice.model.user.TransactionRequest;
import com.reactive.microservices.orderservice.model.user.TransactionStatus;
import org.springframework.stereotype.Service;

@Service
public class OrderServiceResponseFactory {

    public void setTransactionRequest(OrderContext orderContext) {
        TransactionRequest request = new TransactionRequest();
        request.setUserId(orderContext.getOrderPurchaseRequest().getUserId());
        request.setAmount(orderContext.getProduct().getPrice());

        orderContext.setTransactionRequest(request);
    }

    public OrderEntity toEntity(OrderContext orderContext) {
        OrderEntity orderEntity = new OrderEntity();
        orderEntity.setProductId(orderContext.getOrderPurchaseRequest().getProductId());
        orderEntity.setUserId(orderContext.getOrderPurchaseRequest().getUserId());
        orderEntity.setAmount(orderContext.getProduct().getPrice());

        TransactionStatus status = orderContext.getTransactionResponse().getStatus();
        OrderStatus orderStatus = TransactionStatus.APPROVED.equals(status) ? OrderStatus.COMPLETED : OrderStatus.FAILED;

        orderEntity.setStatus(orderStatus);

        return orderEntity;
    }

    public OrderPurchaseResponse toModel(OrderEntity entity) {
        OrderPurchaseResponse response = new OrderPurchaseResponse();
        response.setOrderId(entity.getId());
        response.setUserId(entity.getUserId());
        response.setProductId(entity.getProductId());
        response.setAmount(entity.getAmount());
        response.setStatus(entity.getStatus());

        return response;
    }
}
