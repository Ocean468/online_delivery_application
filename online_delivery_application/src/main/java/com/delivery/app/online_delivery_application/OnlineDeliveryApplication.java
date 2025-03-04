package com.delivery.app.online_delivery_application;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = "com.delivery.app.online_delivery_application")
public class OnlineDeliveryApplication {

	public static void main(String[] args) {
		SpringApplication.run(OnlineDeliveryApplication.class, args);
	}

}
