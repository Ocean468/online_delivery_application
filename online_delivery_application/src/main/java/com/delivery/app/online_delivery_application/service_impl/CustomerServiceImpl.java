package com.delivery.app.online_delivery_application.service_impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.delivery.app.online_delivery_application.dao.CartDao;
import com.delivery.app.online_delivery_application.dao.CustomerDao;
import com.delivery.app.online_delivery_application.dao.ProductDao;
import com.delivery.app.online_delivery_application.dao.UserDao;
import com.delivery.app.online_delivery_application.dto.CustomerDTO;
import com.delivery.app.online_delivery_application.model.Cart;
import com.delivery.app.online_delivery_application.model.Customer;
import com.delivery.app.online_delivery_application.model.Product;
import com.delivery.app.online_delivery_application.model.User;
import com.delivery.app.online_delivery_application.service.CustomerService;

@Service
public class CustomerServiceImpl implements CustomerService{

    
    @Autowired
    private ProductDao productDao;

    @Autowired
    private CustomerDao customerDao;

    @Autowired
    private CartDao cartDao;

    @Autowired
    private UserDao userDao;

    @Override
    public void addToCart(Long userId, Long productId) {
        // Check if the product exists and is available
        Product product = productDao.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        if (!product.getAvailability()) {
            throw new RuntimeException("Product is not available");
        }

        // Check if the user exists (instead of customer)
        User user = userDao.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        // Check if the cart already exists for the user
        Cart cart = cartDao.findByUser_Id(userId).orElse(new Cart());
        cart.setUser(user); // Set the user for the cart

        // Add the product to the cart's products list
        cart.getProducts().add(product);

        // Save the cart
        cartDao.save(cart);
    }


   
    //THis is for cart 
    @Override
    public Optional<Customer> findByEmail(String email) {
        return customerDao.findByEmail(email);
    }




    @Override
public void registerCustomer(CustomerDTO customerDTO) {
    //  Check if the email is already registered in the users table
    Optional<User> existingUser = userDao.findByEmail(customerDTO.getEmail());

    if (existingUser.isPresent()) {
        //  If user exists but is not in the customers table, update as a Customer
        Optional<Customer> existingCustomer = customerDao.findByEmail(customerDTO.getEmail());

        if (existingCustomer.isPresent()) {
            // Customer already exists, just update the loyalty card number if not assigned
            Customer customer = existingCustomer.get();
            if (customer.getLoyaltyCardNumber() == null || customer.getLoyaltyCardNumber().isEmpty()) {
                customer.setLoyaltyCardNumber(generateLoyaltyCardNumber());
                customerDao.save(customer);
            }
            return;
        } else {
            // Convert existing User to a Customer and assign loyalty card
            Customer newCustomer = new Customer();
            newCustomer.setId(existingUser.get().getId()); // Retain existing User ID
            newCustomer.setName(existingUser.get().getName());
            newCustomer.setEmail(existingUser.get().getEmail());
            newCustomer.setPassword(existingUser.get().getPassword());
            newCustomer.setAddress(existingUser.get().getAddress());
            newCustomer.setPhoneNumber(existingUser.get().getPhoneNumber());
            newCustomer.setLoyaltyCardNumber(generateLoyaltyCardNumber());
            customerDao.save(newCustomer);
            return;
        }
    }

    System.out.println("User does not exist. Registering as new customer...");

    // If user is new, register as a Customer
    Customer customer = new Customer();
    customer.setName(customerDTO.getName());
    customer.setEmail(customerDTO.getEmail());
    customer.setPassword(customerDTO.getPassword());  // Consider encrypting passwords
    customer.setAddress(customerDTO.getAddress());
    customer.setPhoneNumber(customerDTO.getPhoneNumber());
    customer.setLoyaltyCardNumber(generateLoyaltyCardNumber());
    customerDao.save(customer);
    System.out.println("New customer saved: " + customer.getEmail());
}

// Helper method to generate a unique loyalty card number
private String generateLoyaltyCardNumber() {
    return "LC-" + System.currentTimeMillis();
}

}
