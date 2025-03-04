package com.delivery.app.online_delivery_application.service_impl;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.delivery.app.online_delivery_application.dao.OrderDao;
import com.delivery.app.online_delivery_application.dao.UserDao;
import com.delivery.app.online_delivery_application.enums.Role;
import com.delivery.app.online_delivery_application.model.Order;
import com.delivery.app.online_delivery_application.model.User;
import com.delivery.app.online_delivery_application.service.AdminService;

@Service
public class AdminServiceImpl implements AdminService {

    @Autowired
    private UserDao userDao;

    @Autowired
    private OrderDao orderDao;

    @Override
    public List<User> getAllUsers() {
        return userDao.findAll();
    }

    @Override
    public void deleteUser(Long id) {
        if (!userDao.existsById(id)) {
            throw new RuntimeException("User not found");
        }
        userDao.deleteById(id);
    }


    @Override
    public void assignDeliveryPersonnel(Long orderId, Long deliveryPersonnelId) {
        System.out.println("Assigning delivery personnel. Order ID: " + orderId + ", Delivery Personnel ID: " + deliveryPersonnelId);
    
        Order order = orderDao.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found: " + orderId));
        System.out.println("Order found: " + order);
    
        User deliveryPersonnel = userDao.findById(deliveryPersonnelId)
                .orElseThrow(() -> new RuntimeException("Delivery personnel not found: " + deliveryPersonnelId));
        System.out.println("Delivery personnel found: " + deliveryPersonnel);
    
        if (deliveryPersonnel.getRole() != Role.DELIVERY_PERSONNEL) { // ✅ Fix enum comparison
            throw new RuntimeException("Assigned user is not a delivery personnel");
        }
    
        order.setDeliveryPersonnel(deliveryPersonnel); // ✅ Fix missing method
        orderDao.save(order);
        System.out.println("Delivery personnel assigned successfully.");
    }
    
    


    @Override
    public String generateSalesReport() {
        List<Order> orders = orderDao.findAll();

        // Use BigDecimal to ensure precision in calculations
        BigDecimal totalRevenue = orders.stream()
            .map(order -> order.getOrderItems()
                .stream()
                .map(item -> item.getProduct().getPrice().multiply(BigDecimal.valueOf(item.getQuantity()))) // ✅ Fixed multiplication
                .reduce(BigDecimal.ZERO, BigDecimal::add)) // ✅ Sum up item prices
            .reduce(BigDecimal.ZERO, BigDecimal::add); // ✅ Sum up all orders

        return "Total Orders: " + orders.size() + ", Total Revenue: $" + totalRevenue;
    }

}
