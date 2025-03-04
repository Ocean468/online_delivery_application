package com.delivery.app.online_delivery_application.dto;

import lombok.Data;

@Data
public class CustomerDTO {
    private String name;
    private String email;
    private String password;
    private String address;
    private String phoneNumber;
}
