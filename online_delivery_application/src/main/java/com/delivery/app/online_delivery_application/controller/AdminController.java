package com.delivery.app.online_delivery_application.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.delivery.app.online_delivery_application.dto.UserResponseDTO;
import com.delivery.app.online_delivery_application.enums.Role;
import com.delivery.app.online_delivery_application.model.User;
import com.delivery.app.online_delivery_application.service.AdminService;
import com.delivery.app.online_delivery_application.service.UserService;


@RestController
@RequestMapping("/api/admin")
public class AdminController {

    @Autowired
    private UserService userService;

    @Autowired
    private AdminService adminService;


    // 1. Get All Users
    @GetMapping("/users")
    public ResponseEntity<List<UserResponseDTO>> getAllUsers() {
        List<User> users = userService.getAllUsers();

        if (users.isEmpty()) {
            return ResponseEntity.noContent().build(); // 204 No Content
        }

        List<UserResponseDTO> userResponseDTOs = users.stream()
                .map(user -> new UserResponseDTO(
                        user.getId(),
                        user.getName() != null ? user.getName() : "N/A",
                        user.getEmail() != null ? user.getEmail() : "N/A",
                        user.getRole() != null ? user.getRole() : Role.USER // Default role
                ))
                .collect(Collectors.toList());

        return ResponseEntity.ok(userResponseDTOs);
    }



    // 2. Get User by ID
    @GetMapping("/users/{id}")
    public ResponseEntity<UserResponseDTO> getUserById(@PathVariable Long id) {
        User user = userService.getUserById(id)
                .orElseThrow(() -> new RuntimeException("User not found")); // Handle missing user
    
        // Map User to UserResponseDTO
        UserResponseDTO userResponseDTO = new UserResponseDTO(user.getId(), user.getName(), user.getEmail(), user.getRole());
    
        return ResponseEntity.ok(userResponseDTO);
    }
    
    // 4. Delete a User
    @DeleteMapping("/users/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseEntity.ok("User deleted successfully");
    }


    
    // 5. Assign a delivery personnel to order
    @PutMapping("/orders/assign/{orderId}")
    public ResponseEntity<String> assignDeliveryPersonnel(
            @PathVariable Long orderId,
            @RequestParam Long deliveryPersonnelId) {
        adminService.assignDeliveryPersonnel(orderId, deliveryPersonnelId);
        return ResponseEntity.ok("Delivery personnel assigned successfully");
    }
    

}











// 5 == PUT = "http://localhost:8080/api/admin/orders/assign/1?deliveryPersonnelId=4"