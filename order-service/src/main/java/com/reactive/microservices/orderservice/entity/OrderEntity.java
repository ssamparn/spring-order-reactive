package com.reactive.microservices.orderservice.entity;

import com.reactive.microservices.orderservice.model.order.OrderStatus;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;


@Data
@Entity
@Table(name = "order_purchase")
public class OrderEntity {

    @Id
    @GeneratedValue
    private Integer id;
    private Integer productId;
    private Integer userId;
    private Integer amount;
    private OrderStatus status;
}
