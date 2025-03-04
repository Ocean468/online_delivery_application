package com.delivery.app.online_delivery_application.service_impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.delivery.app.online_delivery_application.dao.ProductDao;
import com.delivery.app.online_delivery_application.model.Product;
import com.delivery.app.online_delivery_application.service.ProductService;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductDao productDao;

    @Override
    public void addProduct(Product product) {
        productDao.save(product);
    }

    @Override
    public void updateProduct(Long id, Product updatedProduct) {
        Product product = productDao.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        product.setName(updatedProduct.getName());
        product.setDescription(updatedProduct.getDescription());
        product.setPrice(updatedProduct.getPrice());
        product.setAvailability(updatedProduct.getAvailability());

        productDao.save(product);
    }

    @Override
    public List<Product> getAllProducts() {
        return productDao.findAll();
    }

    @Override
    public void deleteProduct(Long id) {
        if (!productDao.existsById(id)) {
            throw new RuntimeException("Product not found");
        }
        productDao.deleteById(id);
    }


    @Override
    public Optional<Product> getProductById(Long productId) {
        return productDao.findById(productId);  // Retrieve product by ID from the repository
    }

}