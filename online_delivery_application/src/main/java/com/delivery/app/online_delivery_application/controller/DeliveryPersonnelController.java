package com.delivery.app.online_delivery_application.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.delivery.app.online_delivery_application.dao.OrderDao;
import com.delivery.app.online_delivery_application.dto.OrderResponseDTO;
import com.delivery.app.online_delivery_application.model.Order;
import com.delivery.app.online_delivery_application.service.DeliveryPersonnelService;
import com.delivery.app.online_delivery_application.service.OrderService;

import lombok.extern.slf4j.Slf4j;


@Slf4j
@RestController
@RequestMapping("/api/delivery-personnel")
public class DeliveryPersonnelController {

     @Autowired
    private DeliveryPersonnelService deliveryPersonnelService;

    @Autowired
    private OrderDao orderDao;

    @Autowired
    private OrderService orderService;

    // 1. View Assigned Orders
    @GetMapping("/orders/{id}")
    public List<Order> getAssignedOrders(Long deliveryPersonnelId) {
        log.info("Fetching orders for delivery personnel ID: {}", deliveryPersonnelId);
    
        List<Order> orders = orderDao.findByDeliveryPersonnel_Id(deliveryPersonnelId);
        
        if (orders.isEmpty()) {
            log.warn("No orders found for delivery personnel ID: {}", deliveryPersonnelId);
        } else {
            log.info("Found {} orders for delivery personnel ID: {}", orders.size(), deliveryPersonnelId);
        }
    
        return orders;
    }


    @GetMapping("/orders/{deliveryPersonnelId}")
    public List<OrderResponseDTO> getOrdersByDeliveryPersonnel(@PathVariable Long deliveryPersonnelId) {
        return orderService.getOrdersByDeliveryPersonnelId(deliveryPersonnelId);
    }


  // 2. Update Delivery Status
    @PutMapping("/orders/status/{orderId}")
    public ResponseEntity<String> updateDeliveryStatus(
            @PathVariable Long orderId,
            @RequestParam String status) {
        deliveryPersonnelService.updateDeliveryStatus(orderId, status);
        return ResponseEntity.ok("Delivery status updated successfully");
    }


}




// 2. == PUT = "http://localhost:8080/api/delivery-personnel/orders/status/1?status=OUT For Delivery"