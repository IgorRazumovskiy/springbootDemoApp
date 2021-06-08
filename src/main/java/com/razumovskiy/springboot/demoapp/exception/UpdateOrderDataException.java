package com.razumovskiy.springboot.demoapp.exception;

import com.razumovskiy.springboot.demoapp.model.MarketplaceOrder;

/**
 * UpdateOrderDataExceptionService in case impossibility update {@link MarketplaceOrder MarketplaceOrder} and save in DB
 *
 * @author Igor Razumovskiy
 * @version 1.0
 * @since 03/06/21
 */
public class UpdateOrderDataException extends RuntimeException {

    public UpdateOrderDataException(String message) {
        super(message);
    }

}
