package com.delivery.app.online_delivery_application.service_impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.delivery.app.online_delivery_application.dao.CartDao;
import com.delivery.app.online_delivery_application.dao.CustomerDao;
import com.delivery.app.online_delivery_application.dao.OrderDao;
import com.delivery.app.online_delivery_application.dao.OrderItemDao;
import com.delivery.app.online_delivery_application.dao.ProductDao;
import com.delivery.app.online_delivery_application.dto.OrderResponseDTO;
import com.delivery.app.online_delivery_application.model.Cart;
import com.delivery.app.online_delivery_application.model.Customer;
import com.delivery.app.online_delivery_application.model.Order;
import com.delivery.app.online_delivery_application.model.OrderItem;
import com.delivery.app.online_delivery_application.model.Product;
import com.delivery.app.online_delivery_application.service.OrderService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private CartDao cartDao;

    @Autowired
    private OrderDao orderDao;

    @Autowired
    private OrderItemDao orderItemDao;

    @Autowired
    private ProductDao productDao;

    @Autowired
    private CustomerDao customerDao;



    @Override
    public void placeOrder(Long customerId) {
        // Get the customer's single cart
        Cart cart = cartDao.findByUser_Id(customerId)
                .orElseThrow(() -> new RuntimeException("Cart not found for customer ID: " + customerId));

        // Check if the cart is empty
        if (cart.getProducts().isEmpty()) {
            throw new RuntimeException("Cart is empty. Please add products before placing an order.");
        }

        // Create a new order
        Order order = new Order();
        Customer customer = customerDao.findById(customerId)
        .orElseThrow(() -> new RuntimeException("Customer not found"));
        order.setCustomer(customer);
        order.setStatus("ORDERED");
        order.setOrderDate(new java.util.Date());
        order = orderDao.save(order);

        // Loop over the products in the cart
        for (Product product : cart.getProducts()) {
            OrderItem orderItem = new OrderItem();
            orderItem.setOrder(order);
            orderItem.setProduct(product);
            orderItem.setQuantity(1); // Assuming quantity is 1 for simplicity
            orderItemDao.save(orderItem);

            // Update product availability
            product.setAvailability(false);
            productDao.save(product);
        }

        // Clear cart after the order is placed
        cart.getProducts().clear();
        cartDao.save(cart);
    }


    @Override
    public String trackOrderStatus(Long orderId) {
        // Fetch the order by ID
        Order order = orderDao.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found"));

        // Return the status of the order
        return order.getStatus(); // Ensure `Order` has a `getStatus()` method

    }



    @Override
    public List<OrderResponseDTO> getOrdersByDeliveryPersonnelId(Long deliveryPersonnelId) {
        return orderDao.findByDeliveryPersonnel_Id(deliveryPersonnelId) // Use orderDao instead of orderRepository
                .stream()
                .map(order -> new OrderResponseDTO(
                        order.getId(),
                        order.getStatus(),
                        order.getOrderDate(),
                        order.getCustomer().getName(),
                        order.getCustomer().getAddress(),
                        order.getCustomer().getPhoneNumber()
                ))
                .collect(Collectors.toList());
    }

}
