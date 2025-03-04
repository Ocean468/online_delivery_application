package com.delivery.app.online_delivery_application.dto;

import java.util.Date;

public class OrderResponseDTO {

    private Long id;
    private String status;
    private Date orderDate;
    private String customerName;
    private String customerAddress;
    private String customerPhoneNumber;

    // Constructor with parameters
    public OrderResponseDTO(Long id, String status, Date orderDate, String customerName, String customerAddress, String customerPhoneNumber) {
        this.id = id;
        this.status = status;
        this.orderDate = orderDate;
        this.customerName = customerName;
        this.customerAddress = customerAddress;
        this.customerPhoneNumber = customerPhoneNumber;
    }

    // Getters and setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Date getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(Date orderDate) {
        this.orderDate = orderDate;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getCustomerAddress() {
        return customerAddress;
    }

    public void setCustomerAddress(String customerAddress) {
        this.customerAddress = customerAddress;
    }

    public String getCustomerPhoneNumber() {
        return customerPhoneNumber;
    }

    public void setCustomerPhoneNumber(String customerPhoneNumber) {
        this.customerPhoneNumber = customerPhoneNumber;
    }
}
