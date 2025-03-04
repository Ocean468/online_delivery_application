package com.delivery.app.online_delivery_application.model;


import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "customers")
@PrimaryKeyJoinColumn(name = "id") 
public class Customer extends User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id", unique = true, nullable = false)
    private User user;

    @Column(name = "loyalty_card_number")
    private String loyaltyCardNumber; // Additional customer-specific field

    @PrePersist
    public void generateLoyaltyCardNumber() {
        if (this.loyaltyCardNumber == null) {  // Prevent overwriting
            this.loyaltyCardNumber = UUID.randomUUID().toString();
        }
    }

    // Getters and Setters
}
