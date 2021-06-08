package com.razumovskiy.springboot.demoapp.service;

import com.razumovskiy.springboot.demoapp.dto.OrderItemInputDto;
import com.razumovskiy.springboot.demoapp.model.MarketplaceOrder;
import com.razumovskiy.springboot.demoapp.dto.OrderInputDto;
import com.razumovskiy.springboot.demoapp.model.OrderItem;
import org.springframework.stereotype.Service;

import java.util.Currency;
import java.util.Optional;

/**
 * MarketplaceOrderConverter convert {@link OrderInputDto OrderInputDto} to {@link MarketplaceOrder MarketplaceOrder}
 *
 * @author Igor Razumovskiy
 * @version 1.0
 * @since 08/06/2021
 */
@Service
public class MarketplaceOrderConverter {

    public MarketplaceOrder convertDtoToOrder(OrderInputDto orderInputDto, MarketplaceOrder order) {
        order.setCurrency(Currency.getInstance(orderInputDto.getCurrency()));
        order.setCustomerFullName(orderInputDto.getCustomerFullName());
        order.setCustomerEmail(orderInputDto.getCustomerEmail());

        if (!order.getOrderItems().isEmpty()) {
            order.getOrderItems().removeIf(
                    existingOrderItem -> orderInputDto.getOrderItems().stream()
                            .noneMatch(inputOrderItem -> inputOrderItem.getProductId().equals(existingOrderItem.getId())));
        }

        for (OrderItemInputDto orderItemInputDto : orderInputDto.getOrderItems()) {
            OrderItem orderItem;
            Optional<OrderItem> existingItem = order.getOrderItems().stream()
                    .filter(oi -> oi.getProductId().equals(orderItemInputDto.getProductId()))
                    .findAny();
            if (existingItem.isPresent()) {
                orderItem = existingItem.get();
            } else {
                orderItem = new OrderItem();
                orderItem.setProductId(orderItemInputDto.getProductId());
                orderItem.setProductName(orderItemInputDto.getProductName());
                orderItem.setOfferPrice(orderItemInputDto.getOfferPrice());
                orderItem.setCount(orderItemInputDto.getCount());
                orderItem.setTotalCost(orderItemInputDto.getCount() * orderItemInputDto.getOfferPrice());
            }
            order.addOrderItem(orderItem);
        }
        return order;
    }
}
