package com.delivery.app.online_delivery_application.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.delivery.app.online_delivery_application.model.Order;



@Repository
public interface OrderDao extends JpaRepository<Order, Long> {
    
    //  Define a method to find orders assigned to a specific delivery personnel
    List<Order> findByDeliveryPersonnel_Id(Long deliveryPersonnelId);
}



