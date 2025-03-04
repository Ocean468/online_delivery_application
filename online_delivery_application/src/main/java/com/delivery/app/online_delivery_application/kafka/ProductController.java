package com.delivery.app.online_delivery_application.kafka;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/product")
public class ProductController {

    @Autowired
    private ProductLocationProducer producer;

    @PostMapping("/{productId}/update-location")
    public String updateProductLocation(@PathVariable String productId, @RequestParam String location) {
        producer.sendLocationUpdate(productId, location);
        return "Location update sent for Product ID: " + productId;
    }
}