package com.delivery.app.online_delivery_application.dto;

import lombok.Data;

@Data
public class FeedbackResponseDTO {
    private String username; // Customer name
    private int rating;
    private String review;
}