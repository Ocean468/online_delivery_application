package com.delivery.app.online_delivery_application.service;

import java.util.List;

import com.delivery.app.online_delivery_application.model.User;

public interface AdminService {
    List<User> getAllUsers();
    void deleteUser(Long id);
    void assignDeliveryPersonnel(Long orderId, Long deliveryPersonnelId);
    String generateSalesReport();
}

