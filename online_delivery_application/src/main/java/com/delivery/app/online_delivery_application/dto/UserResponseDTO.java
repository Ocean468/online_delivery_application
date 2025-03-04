package com.delivery.app.online_delivery_application.dto;
import com.delivery.app.online_delivery_application.enums.Role;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserResponseDTO {
    private Long id;
    private String name;
    private String email;
    private Role role;  // Ensure Role is used here

}

