package com.delivery.app.online_delivery_application.dao;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.delivery.app.online_delivery_application.model.Cart;


public interface CartDao extends JpaRepository<Cart, Long> {
    Optional<Cart> findByUser_Id(Long userId);  // ✅ Corrected method name
}













