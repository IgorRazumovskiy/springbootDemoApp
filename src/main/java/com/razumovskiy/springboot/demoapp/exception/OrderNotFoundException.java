package com.razumovskiy.springboot.demoapp.exception;

import com.razumovskiy.springboot.demoapp.model.MarketplaceOrder;

/**
 * OrderNotFoundException in case not found {@link MarketplaceOrder MarketplaceOrder} in DB
 *
 * @author Igor Razumovskiy
 * @version 1.0
 * @since 03/06/21
 */
public class OrderNotFoundException extends RuntimeException {

    public OrderNotFoundException(Integer id) {
        super("Order not found for Id " +  id);
    }
}
