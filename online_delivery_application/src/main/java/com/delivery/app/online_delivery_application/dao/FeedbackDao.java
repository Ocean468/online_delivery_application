package com.delivery.app.online_delivery_application.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.delivery.app.online_delivery_application.model.Feedback;

public interface FeedbackDao extends JpaRepository<Feedback, Long> {
    // List<Feedback> findByProductId(Long productId);

    @Query("SELECT f FROM Feedback f WHERE f.product.id = :productId")
    List<Feedback> findByProductId(@Param("productId") Long productId);

}
