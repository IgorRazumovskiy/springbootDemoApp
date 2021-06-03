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
import java.util.ArrayList;
import java.util.Collection;
import java.util.Currency;
import java.util.List;

@Service
    public class MarketplaceOrderServiceImpl implements MarketplaceOrderService {

    @Autowired
    private MarketplaceOrderRepository marketplaceOrderRepository;

    @Override
    public List<MarketplaceOrder> findAll() {
        return marketplaceOrderRepository.findAll();
    }


    @Override
    public MarketplaceOrder findOne(Integer id) {
        return marketplaceOrderRepository.findById(id).orElseThrow(() -> new OrderNotFoundException(id));
    }

    @Override
    public MarketplaceOrder create(OrderInputDto orderInputDto) {
        MarketplaceOrder orderToSave = new MarketplaceOrder();
        fillMarketplaceOrderData(orderInputDto, orderToSave);
        return marketplaceOrderRepository.saveAndFlush(orderToSave);
    }

    private void fillMarketplaceOrderData(OrderInputDto orderInputDto, MarketplaceOrder order) {
        order.setCurrency(Currency.getInstance(orderInputDto.getCurrency()));
        order.setCustomerFullName(orderInputDto.getCustomerFullName());
        order.setCustomerEmail(orderInputDto.getCustomerEmail());

        order.getOrderItems().clear();
        Collection<OrderItem> orderItems = new ArrayList<>();
        for (OrderItemInputDto orderItemInputDto : orderInputDto.getOrderItems()) {
            OrderItem orderItem = new OrderItem();
            orderItem.setMarketplaceOrder(order);
            orderItem.setProductId(orderItemInputDto.getProductId());
            orderItem.setProductName(orderItemInputDto.getProductName());
            orderItem.setOfferPrice(orderItemInputDto.getOfferPrice());
            orderItem.setCount(orderItemInputDto.getCount());
            orderItem.setTotalCost(orderItemInputDto.getCount() * orderItemInputDto.getOfferPrice());
            orderItems.add(orderItem);
        }
        order.getOrderItems().addAll(orderItems);
        order.setDateTime(ZonedDateTime.now());
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
        fillMarketplaceOrderData(orderInputDto, orderToUpdate);
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
