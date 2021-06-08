package com.razumovskiy.springboot.demoapp.service;

import com.razumovskiy.springboot.demoapp.db.MarketplaceOrderRepository;
import com.razumovskiy.springboot.demoapp.dto.OrderInputDto;
import com.razumovskiy.springboot.demoapp.dto.OrderItemInputDto;
import com.razumovskiy.springboot.demoapp.exception.OrderNotFoundException;
import com.razumovskiy.springboot.demoapp.exception.UpdateOrderDataException;
import com.razumovskiy.springboot.demoapp.model.MarketplaceOrder;
import com.razumovskiy.springboot.demoapp.model.OrderItem;
import com.razumovskiy.springboot.demoapp.model.OrderStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.ZonedDateTime;
import java.util.*;

/**
 * Implementation of {@link MarketplaceOrderService MarketplaceOrderService}
 *
 * @author Igor Razumovskiy
 * @version 1.0
 * @since 03/06/21
 */
@Service
public class MarketplaceOrderServiceImpl implements MarketplaceOrderService {

    @Autowired
    private MarketplaceOrderRepository marketplaceOrderRepository;

    @Autowired
    private MarketplaceOrderConverter marketplaceOrderConverter;

    @Override
    @Transactional(readOnly = true)
    public List<MarketplaceOrder> findAll() {
        return marketplaceOrderRepository.findAll();
    }


    @Override
    public MarketplaceOrder findOne(Integer id) {
        return marketplaceOrderRepository.findById(id).orElseThrow(() -> new OrderNotFoundException(id));
    }

    @Override
    @Transactional
    public MarketplaceOrder create(OrderInputDto orderInputDto) {
        MarketplaceOrder orderToSave = new MarketplaceOrder();
        orderToSave.setDateTime(ZonedDateTime.now());
        orderToSave = marketplaceOrderConverter.convertDtoToOrder(orderInputDto, orderToSave);
        return marketplaceOrderRepository.saveAndFlush(orderToSave);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public MarketplaceOrder update(OrderInputDto orderInputDto) {
        Integer id = orderInputDto.getId();
        MarketplaceOrder orderToUpdate = marketplaceOrderRepository.findById(id)
                .orElseThrow(() -> new OrderNotFoundException(id));
        if (!orderToUpdate.getOrderStatus().equals(OrderStatus.PENDING)) {
            throw new UpdateOrderDataException("Order with such status can't be updated! Id=" + id);
        }
        orderToUpdate = marketplaceOrderConverter.convertDtoToOrder(orderInputDto, orderToUpdate);
        return marketplaceOrderRepository.saveAndFlush(orderToUpdate);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(Integer id) {
        MarketplaceOrder marketplaceOrder = marketplaceOrderRepository.findById(id).orElseThrow(() -> new OrderNotFoundException(id));
        if (marketplaceOrder.getOrderStatus().equals(OrderStatus.SUCCESS)) {
            throw new UpdateOrderDataException("Order can't be removed with SUCCESS status! Id=" + id);
        }
        marketplaceOrderRepository.deleteById(id);
    }
}
