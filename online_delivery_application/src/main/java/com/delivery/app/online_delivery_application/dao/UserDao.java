package com.delivery.app.online_delivery_application.dao;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.delivery.app.online_delivery_application.model.User;


public interface UserDao extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
}

