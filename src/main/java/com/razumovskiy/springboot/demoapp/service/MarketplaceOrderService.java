package com.razumovskiy.springboot.demoapp.service;

import com.razumovskiy.springboot.demoapp.dto.OrderInputDto;
import com.razumovskiy.springboot.demoapp.exception.UpdateOrderDataException;
import com.razumovskiy.springboot.demoapp.exception.OrderNotFoundException;
import com.razumovskiy.springboot.demoapp.model.MarketplaceOrder;
import com.razumovskiy.springboot.demoapp.model.OrderStatus;

import java.util.List;

/**
 * Service for {@link MarketplaceOrder MarketplaceOrder} CRUD methods
 *
 * @author Igor Razumovskiy
 * @version 1.0
 * @since 03/06/21
 */
public interface MarketplaceOrderService {

    /**
     * Retrieves all MarketplaceOrders from the database
     *
     * @return list of {@link MarketplaceOrder MarketplaceOrder}
     */
    List<MarketplaceOrder> findAll();

    /**
     * Retrieves the MarketplaceOrder by id from the database
     *
     * @param id the ID of {@link MarketplaceOrder MarketplaceOrder}
     * @return the {@link MarketplaceOrder MarketplaceOrder}
     */
    MarketplaceOrder findOne(Integer id);

    /**
     * Save MarketplaceOrder in database from OrderInputDto data
     *
     * @param orderInputDto the {@link OrderInputDto OrderInputDto} input form data
     * @return created {@link MarketplaceOrder MarketplaceOrder}
     */
    MarketplaceOrder create(OrderInputDto orderInputDto);

    /**
     * Update MarketplaceOrder in database from OrderInputDto data
     *
     * @param orderInputDto the {@link OrderInputDto OrderInputDto} input form data
     * @return updated {@link MarketplaceOrder MarketplaceOrder}
     * @throws UpdateOrderDataException in case {@link MarketplaceOrder MarketplaceOrder} has
     *                                  ANY {@link OrderStatus OrderStatus} except PENDING
     * @throws OrderNotFoundException   in case {@link MarketplaceOrder MarketplaceOrder} not found by ID
     */
    MarketplaceOrder update(OrderInputDto orderInputDto);

    /**
     * Remove MarketplaceOrder by id from database
     *
     * @param id the ID of {@link MarketplaceOrder MarketplaceOrder}
     * @throws UpdateOrderDataException in case {@link MarketplaceOrder MarketplaceOrder} has
     *                                  {@link OrderStatus OrderStatus} SUCCESS
     * @throws OrderNotFoundException   in case {@link MarketplaceOrder MarketplaceOrder} not found by ID
     */
    void delete(Integer id);
}
