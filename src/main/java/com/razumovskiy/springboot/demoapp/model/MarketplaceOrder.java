package com.razumovskiy.springboot.demoapp.model;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Currency;
import java.util.Objects;

/**
 * {@link MarketplaceOrder MarketplaceOrder} entity
 *
 * @author Igor Razumovskiy
 * @version 1.0
 * @since 03/06/21
 */
@Entity
@Table(name = "marketplace_order")
public class MarketplaceOrder implements Serializable {

    private static final long serialVersionUID = -4147559958958899028L;

    @Id
    @SequenceGenerator(name = "marketplace_order_mo_id_seq",
            sequenceName = "marketplace_order_mo_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "marketplace_order_mo_id_seq")
    @Column(name = "mo_id")
    private Integer id;

    @Column(name = "mo_time")
    private ZonedDateTime dateTime;

    @Column(name = "mo_currency")
    private Currency currency;

    @NotEmpty(message = "Customer name can not be empty")
    @NotNull(message = "Customer name can not be null")
    @Column(name = "mo_customer_full_name")
    private String customerFullName;

    @NotEmpty(message = "Customer email can not be empty")
    @NotNull(message = "Customer email can not be null")
    @Column(name = "mo_customer_email")
    private String customerEmail;

    @Enumerated(value = EnumType.STRING)
    @Column(name = "mo_order_status")
    private OrderStatus orderStatus = OrderStatus.PENDING;

    @OneToMany(mappedBy = "marketplaceOrder", cascade = CascadeType.ALL)
    private Collection<OrderItem> orderItems = new ArrayList<>(1);

    public MarketplaceOrder() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public ZonedDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(ZonedDateTime dateTime) {
        this.dateTime = dateTime;
    }

    public Currency getCurrency() {
        return currency;
    }

    public void setCurrency(Currency currency) {
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

    public OrderStatus getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(OrderStatus orderStatus) {
        this.orderStatus = orderStatus;
    }

    public Collection<OrderItem> getOrderItems() {
        return orderItems;
    }

    public void setOrderItems(Collection<OrderItem> orderItems) {
        this.orderItems = orderItems;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MarketplaceOrder that = (MarketplaceOrder) o;
        return Objects.equals(id, that.id) && Objects.equals(dateTime, that.dateTime) && Objects.equals(currency, that.currency)
                && Objects.equals(customerFullName, that.customerFullName) && Objects.equals(customerEmail, that.customerEmail)
                && orderStatus == that.orderStatus && Objects.equals(orderItems, that.orderItems);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, dateTime, currency, customerFullName, customerEmail, orderStatus, orderItems);
    }
}
