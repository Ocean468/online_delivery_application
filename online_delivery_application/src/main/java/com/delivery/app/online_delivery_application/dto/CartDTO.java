package com.delivery.app.online_delivery_application.dto;

import java.util.List;

import com.delivery.app.online_delivery_application.model.Product;

import lombok.Data;


@Data
public class CartDTO {
    private Long cartId;
    private List<Product> products;

    // ✅ Add the required constructor
    public CartDTO(Long cartId, List<Product> products) {
        this.cartId = cartId;
        this.products = products;
    }

}
