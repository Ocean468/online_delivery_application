package com.delivery.app.online_delivery_application.service;

import java.util.List;

import com.delivery.app.online_delivery_application.dto.FeedbackResponseDTO;

public interface FeedbackService {
    void saveFeedback(Long customerId, Long productId, int rating, String review);
    List<FeedbackResponseDTO> getFeedbackByProduct(Long productId);
}