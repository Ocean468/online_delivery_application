package com.delivery.app.online_delivery_application.service;

import java.util.List;
import java.util.Optional;

import com.delivery.app.online_delivery_application.model.Product;

public interface ProductService {
    public List<Product> getAllProducts();
    void addProduct(Product product);
    void updateProduct(Long id, Product updatedProduct);
    void deleteProduct(Long id);
    Optional<Product> getProductById(Long productId);

}
