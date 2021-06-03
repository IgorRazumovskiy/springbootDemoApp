package com.razumovskiy.springboot.demoapp.exception;

public class OrderNotFoundException extends RuntimeException {

    public OrderNotFoundException(Integer id) {
        super("Order not found for Id " +  id);
    }
}
