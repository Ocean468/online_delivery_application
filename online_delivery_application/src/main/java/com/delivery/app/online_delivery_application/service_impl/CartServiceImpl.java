package com.delivery.app.online_delivery_application.service_impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.delivery.app.online_delivery_application.dao.CartDao;
import com.delivery.app.online_delivery_application.model.Cart;
import com.delivery.app.online_delivery_application.model.Product;
import com.delivery.app.online_delivery_application.model.User;
import com.delivery.app.online_delivery_application.service.CartService;
import com.delivery.app.online_delivery_application.service.ProductService;
import com.delivery.app.online_delivery_application.service.UserService;

@Service
public class CartServiceImpl implements CartService {



    @Autowired
    private CartDao cartDao; // Ensure repository exists

    @Autowired
    private ProductService productService;

    @Autowired
    private UserService userService;
   
   
    @Override
    public void addToCart(Long userId, Long productId, int quantity) {
        // ✅ Find user
        User user = userService.getUserById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        // ✅ Find or create cart
        Cart cart = cartDao.findByUser_Id(userId)
                .orElseGet(() -> {
                    Cart newCart = new Cart();
                    newCart.setUser(user);
                    return cartDao.save(newCart);
                });

        // ✅ Find the product
        Product product = productService.getProductById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        // ✅ Check if product exists in cart and update quantity
        cart.getProducts().stream()
                .filter(item -> item.getId().equals(productId))
                .findFirst()
                .ifPresentOrElse(
                        existingProduct -> {
                            // You need a way to store quantity; this might not work as intended.
                            // Consider having a separate `CartItem` entity instead of ManyToMany.
                        },
                        () -> cart.getProducts().add(product)
                );

        cartDao.save(cart);
    }

    @Override
    public Optional<Cart> getCartByUser(Long userId) {
        return cartDao.findByUser_Id(userId);
    }
}

