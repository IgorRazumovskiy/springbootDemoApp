package com.razumovskiy.springboot.demoapp.dto;

import java.util.Set;

public class OrderInputDto {

    private Integer id;

    private String currency;

    private String customerFullName;

    private String customerEmail;

    private Set<OrderItemInputDto> orderItems;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getCustomerFullName() {
        return customerFullName;
    }

    public void setCustomerFullName(String customerFullName) {
        this.customerFullName = customerFullName;
    }

    public String getCustomerEmail() {
        return customerEmail;
    }

    public void setCustomerEmail(String customerEmail) {
        this.customerEmail = customerEmail;
    }

    public Set<OrderItemInputDto> getOrderItems() {
        return orderItems;
    }

    public void setOrderItems(Set<OrderItemInputDto> orderItems) {
        this.orderItems = orderItems;
    }
}
