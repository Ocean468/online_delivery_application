package com.delivery.app.online_delivery_application.service;

import java.util.List;

import com.delivery.app.online_delivery_application.model.Order;

public interface DeliveryPersonnelService {
    List<Order> getAssignedOrders(Long deliveryPersonnelId);
    void updateDeliveryStatus(Long orderId, String status);
}
