package com.razumovskiy.springboot.demoapp.dto;

import com.razumovskiy.springboot.demoapp.model.MarketplaceOrder;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Set;

/**
 * OrderInputDto for input data of {@link MarketplaceOrder MarketplaceOrder}
 *
 * @author Igor Razumovskiy
 * @version 1.0
 * @since 03/06/21
 */
public class OrderInputDto {

    private Integer id;

    private String currency;

    @NotEmpty(message = "Customer name can not be empty")
    @NotNull(message = "Customer name can not be null")
    private String customerFullName;

    @NotEmpty(message = "Customer email can not be empty")
    @NotNull(message = "Customer email can not be null")
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
