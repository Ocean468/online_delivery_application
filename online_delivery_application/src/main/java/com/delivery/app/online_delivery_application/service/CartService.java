package com.delivery.app.online_delivery_application.service;

import java.util.Optional;

import com.delivery.app.online_delivery_application.model.Cart;

public interface CartService {
      void addToCart(Long userId, Long productId, int quantity);
      Optional<Cart> getCartByUser(Long userId);

}
  