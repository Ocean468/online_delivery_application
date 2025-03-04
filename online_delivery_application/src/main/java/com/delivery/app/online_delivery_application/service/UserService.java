package com.delivery.app.online_delivery_application.service;

import java.util.List;
import java.util.Optional;

import com.delivery.app.online_delivery_application.model.User;

public interface UserService {
    List<User> getAllUsers();
    // User getUserById(Long id);
        Optional<User> getUserById(Long userId); 
    // User addUser(User user);
    void deleteUser(Long id);
    User registerUser(User user); // New method for signup
    Optional<User> findByEmail(String email);
}
