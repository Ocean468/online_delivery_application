package com.delivery.app.online_delivery_application.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.delivery.app.online_delivery_application.dto.OrderResponseDTO;

@Service
public interface OrderService {
    void placeOrder(Long customerId);
    String trackOrderStatus(Long orderId);

    // New method to fetch orders for a delivery personnel
     List<OrderResponseDTO> getOrdersByDeliveryPersonnelId(Long deliveryPersonnelId);
}

