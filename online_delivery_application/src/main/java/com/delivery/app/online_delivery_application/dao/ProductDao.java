package com.delivery.app.online_delivery_application.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.delivery.app.online_delivery_application.model.Product;


public interface ProductDao extends JpaRepository<Product, Long> {}


