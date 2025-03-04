package com.delivery.app.online_delivery_application.dto;

import lombok.Data;

@Data
public class FeedbackDTO {
    private Long customerId;
    private Long productId;
    private int rating;
    private String review;
}
