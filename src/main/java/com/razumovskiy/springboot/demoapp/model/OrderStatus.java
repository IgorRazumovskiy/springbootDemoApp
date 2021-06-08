package com.razumovskiy.springboot.demoapp.model;

/**
 * OrderStatus of {@link MarketplaceOrder MarketplaceOrder}
 *
 * @author Igor Razumovskiy
 * @version 1.0
 * @since 03/06/21
 */
public enum OrderStatus {
    PENDING, SUCCESS, DUPLICATED, FAILED
}
