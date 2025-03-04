package com.delivery.app.online_delivery_application.kafka;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class ProductLocationConsumer {

    @KafkaListener(topics = "product_location", groupId = "group_id")
    public void listen(String message) {
        System.out.println(" Received location update: " + message);
    }
}