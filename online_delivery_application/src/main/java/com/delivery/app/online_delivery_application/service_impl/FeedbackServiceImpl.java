package com.delivery.app.online_delivery_application.service_impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.delivery.app.online_delivery_application.dao.CustomerDao;
import com.delivery.app.online_delivery_application.dao.FeedbackDao;
import com.delivery.app.online_delivery_application.dao.ProductDao;
import com.delivery.app.online_delivery_application.dto.FeedbackResponseDTO;
import com.delivery.app.online_delivery_application.model.Customer;
import com.delivery.app.online_delivery_application.model.Feedback;
import com.delivery.app.online_delivery_application.model.Product;
import com.delivery.app.online_delivery_application.service.FeedbackService;

@Service
public class FeedbackServiceImpl implements FeedbackService {

    @Autowired
    private FeedbackDao feedbackDao;

    @Autowired
    private CustomerDao customerDao;

    @Autowired
    private ProductDao productDao;

    @Override
    public void saveFeedback(Long customerId, Long productId, int rating, String review) {
        // Verify if customer exists
        Customer customer = customerDao.findById(customerId)
                .orElseThrow(() -> new RuntimeException("Customer not found"));

        // Verify if product exists
        Product product = productDao.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        // Create and save Feedback entity
        Feedback feedback = new Feedback();
        feedback.setCustomer(customer);
        feedback.setProduct(product);
        feedback.setRating(rating);
        feedback.setReview(review);
        feedbackDao.save(feedback);
    }

    @Override
    public List<FeedbackResponseDTO> getFeedbackByProduct(Long productId) {
        List<Feedback> feedbackList = feedbackDao.findByProductId(productId);
        if (feedbackList.isEmpty()) {
            System.out.println("No feedback found for product ID: " + productId);
        } else {
            System.out.println("Feedback found: " + feedbackList);
        }
    
        return feedbackList.stream()
                .map(feedback -> {
                    FeedbackResponseDTO dto = new FeedbackResponseDTO();
                    dto.setUsername(feedback.getCustomer().getName());
                    dto.setRating(feedback.getRating());
                    dto.setReview(feedback.getReview());
                    return dto;
                })
                .collect(Collectors.toList());
    }
    
}
