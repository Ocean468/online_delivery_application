package com.delivery.app.online_delivery_application.service_impl;


import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.delivery.app.online_delivery_application.dao.CustomerDao;
import com.delivery.app.online_delivery_application.dao.UserDao;
import com.delivery.app.online_delivery_application.enums.Role;
import com.delivery.app.online_delivery_application.model.Customer;
import com.delivery.app.online_delivery_application.model.User;
import com.delivery.app.online_delivery_application.service.UserService;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;

    @Autowired
    private CustomerDao customerDao;

     @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Override
    public List<User> getAllUsers() {
        return userDao.findAll();
    }

    @Override
    public Optional<User> getUserById(Long id) {
        return userDao.findById(id);
    }



    @Override
    public void deleteUser(Long id) {
        if (!userDao.existsById(id)) {
            throw new RuntimeException("User not found");
        }
        userDao.deleteById(id);
    }


    @Override
    public User registerUser(User user) {
        // Check if email already exists in User table
        if (userDao.findByEmail(user.getEmail()).isPresent()) {
            throw new RuntimeException("Email is already registered");
        }
    
        // Encrypt the password
        user.setPassword(passwordEncoder.encode(user.getPassword()));
    
        // Save user in `users` table
        User savedUser = userDao.save(user);
        System.out.println(" User saved successfully: " + savedUser.getEmail());
    
        // If the user is a CUSTOMER, save it as a reference in the `customers` table
        if (user.getRole() == Role.CUSTOMER) {
            Customer customer = new Customer();
            customer.setUser(savedUser);  // Set reference to User entity
            customer.setLoyaltyCardNumber(generateLoyaltyCardNumber()); // Example method for generating a card number
            customerDao.save(customer); // Save the customer reference
            System.out.println(" Customer reference added to customers table");
        }
    
        return savedUser;
    }
    

    private String generateLoyaltyCardNumber() {
        return "LC" + System.currentTimeMillis(); // Generates a unique loyalty card number
    }
    
    

    @Override
    public Optional<User> findByEmail(String email) {
        return userDao.findByEmail(email);  // Assuming you have this method in your UserDao
    }
    

    
}
