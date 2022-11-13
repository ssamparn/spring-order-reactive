package com.reactive.microservices.orderservice.entity;

import com.reactive.microservices.orderservice.model.order.OrderStatus;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

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
