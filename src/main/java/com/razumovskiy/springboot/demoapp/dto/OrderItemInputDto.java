package com.razumovskiy.springboot.demoapp.dto;

import com.razumovskiy.springboot.demoapp.model.OrderItem;

import javax.validation.constraints.NotNull;

/**
 * OrderItemInputDto for input data of {@link OrderItem OrderItem}
 *
 * @author Igor Razumovskiy
 * @version 1.0
 * @since 03/06/21
 */
public class OrderItemInputDto {

    @NotNull(message = "ProductId can not be null")
    private Integer productId;

    private String productName;

    private Integer offerPrice;

    private Integer count;

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public Integer getOfferPrice() {
        return offerPrice;
    }

    public void setOfferPrice(Integer offerPrice) {
        this.offerPrice = offerPrice;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }
}
