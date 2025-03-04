package com.delivery.app.online_delivery_application.service_impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.delivery.app.online_delivery_application.dao.OrderDao;
import com.delivery.app.online_delivery_application.model.Order;
import com.delivery.app.online_delivery_application.service.DeliveryPersonnelService;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class DeliveryPersonnelServiceImpl implements DeliveryPersonnelService {

    @Autowired
    private OrderDao orderDao;

    public List<Order> getAssignedOrders(Long deliveryPersonnelId) {
        List<Order> orders = orderDao.findByDeliveryPersonnel_Id(deliveryPersonnelId);
        if (orders.isEmpty()) {
            log.info("No orders found for delivery personnel ID: {}", deliveryPersonnelId);
        }
        return orders;
    }

    @Override
    public void updateDeliveryStatus(Long orderId, String status) {
        Order order = orderDao.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found"));

        if (order.getDeliveryPersonnel() == null) {
            throw new RuntimeException("Order has not been assigned to any delivery personnel");
        }

        order.setStatus(status); // Update the order status
        orderDao.save(order);
    }
}
