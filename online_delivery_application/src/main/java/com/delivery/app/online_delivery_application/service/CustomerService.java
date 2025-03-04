package com.delivery.app.online_delivery_application.service;

import java.util.Optional;

import com.delivery.app.online_delivery_application.dto.CustomerDTO;
import com.delivery.app.online_delivery_application.model.Customer;

public interface CustomerService {
    void addToCart(Long customerId, Long productId);
    void registerCustomer(CustomerDTO customerDTO);
    Optional<Customer> findByEmail(String email);
}
