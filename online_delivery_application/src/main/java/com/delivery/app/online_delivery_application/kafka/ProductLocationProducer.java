package com.delivery.app.online_delivery_application.kafka;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class ProductLocationProducer {

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    private static final String TOPIC = "product_location";

    public void sendLocationUpdate(String productId, String location) {
        String message = "Product ID: " + productId + " is at location: " + location;
        kafkaTemplate.send(TOPIC, message);
        System.out.println(" Sent: " + message);
    }
}
