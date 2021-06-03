package com.razumovskiy.springboot.demoapp.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.Positive;
import java.io.Serializable;
import java.util.Objects;

/**
 * {@link OrderItem OrderItem} entity
 *
 * @author Igor Razumovskiy
 * @version 1.0
 * @since 03/06/21
 */
@Entity
@Table(name = "marketplace_order_item")
public class OrderItem implements Serializable {

    private static final long serialVersionUID = -3046761141951365576L;

    @Id
    @SequenceGenerator(name = "marketplace_order_item_moi_id_seq",
            sequenceName = "marketplace_order_item_moi_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "marketplace_order_item_moi_id_seq")
    @Column(name = "moi_id")
    private Integer id;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "moi_order_id")
    private MarketplaceOrder marketplaceOrder;

    @Column(name = "moi_product_id")
    private Integer productId;

    @Column(name = "moi_product_name")
    private String productName;

    @Positive(message = "offerPrice must be positive")
    @Column(name = "moi_offer_price")
    private Integer offerPrice;

    @Positive(message = "count must be positive")
    @Column(name = "moi_count")
    private Integer count;

    @Column(name = "moi_total_item_cost")
    private Integer totalCost;

    public OrderItem() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public MarketplaceOrder getMarketplaceOrder() {
        return marketplaceOrder;
    }

    public void setMarketplaceOrder(MarketplaceOrder marketplaceOrder) {
        this.marketplaceOrder = marketplaceOrder;
    }

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

    public Integer getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(Integer totalCost) {
        this.totalCost = totalCost;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OrderItem orderItem = (OrderItem) o;
        return Objects.equals(id, orderItem.id) && Objects.equals(marketplaceOrder, orderItem.marketplaceOrder)
                && Objects.equals(productId, orderItem.productId) && Objects.equals(productName, orderItem.productName)
                && Objects.equals(offerPrice, orderItem.offerPrice) && Objects.equals(count, orderItem.count)
                && Objects.equals(totalCost, orderItem.totalCost);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, marketplaceOrder, productId, productName, offerPrice, count, totalCost);
    }
}
