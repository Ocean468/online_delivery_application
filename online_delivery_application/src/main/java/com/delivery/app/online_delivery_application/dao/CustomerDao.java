package com.delivery.app.online_delivery_application.dao;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.delivery.app.online_delivery_application.model.Customer;


public interface CustomerDao extends JpaRepository<Customer, Long> {
    Optional<Customer> findByEmail(String email);
}


