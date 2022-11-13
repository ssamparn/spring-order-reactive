package com.reactive.microservices.orderservice.model.product;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor(staticName = "create")
public class Product {
    private String id;
    private String description;
    private Integer price;
}
