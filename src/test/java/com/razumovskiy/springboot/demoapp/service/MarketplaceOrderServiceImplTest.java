package com.razumovskiy.springboot.demoapp.service;

import com.razumovskiy.springboot.demoapp.db.MarketplaceOrderRepository;
import com.razumovskiy.springboot.demoapp.dto.OrderInputDto;
import com.razumovskiy.springboot.demoapp.exception.OrderNotFoundException;
import com.razumovskiy.springboot.demoapp.exception.UpdateOrderDataException;
import com.razumovskiy.springboot.demoapp.model.MarketplaceOrder;
import com.razumovskiy.springboot.demoapp.model.OrderStatus;
import org.assertj.core.util.Sets;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

class MarketplaceOrderServiceImplTest {

    @InjectMocks
    private MarketplaceOrderServiceImpl marketplaceOrderService;

    @Mock
    private MarketplaceOrderRepository marketplaceOrderRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void findAllShouldReturnListWithAllOrders() {
        when(marketplaceOrderRepository.findAll()).thenReturn(createMarketplaceOrdersList());
        assertEquals(2, marketplaceOrderService.findAll().size());
    }

    @Test
    void findAllShouldReturnEmptyListWhenNoOrders() {
        when(marketplaceOrderRepository.findAll()).thenReturn(Collections.emptyList());
        assertEquals(0, marketplaceOrderService.findAll().size());
    }

    @Test
    void findOneShouldReturnOrderWhenFound() {
        MarketplaceOrder order1 = new MarketplaceOrder();
        order1.setId(1);
        when(marketplaceOrderRepository.findById(anyInt())).thenReturn(Optional.of(order1));
        assertEquals(order1, marketplaceOrderService.findOne(1));
        assertNotNull(marketplaceOrderService.findOne(1));
    }

    @Test
    void findOneShouldThrowOrderNotFoundExceptionWhenNotFound() {
        MarketplaceOrder order1 = new MarketplaceOrder();
        order1.setId(1);
        when(marketplaceOrderRepository.findById(anyInt())).thenReturn(Optional.of(order1));
        assertThrows(OrderNotFoundException.class, () -> {
            marketplaceOrderService.findOne(null);
        });
    }

    @Test
    @Disabled
    void createShouldReturnOrderWhenSaved() {
        OrderInputDto orderInputDto = new OrderInputDto();
        orderInputDto.setCurrency("EUR");
        orderInputDto.setCustomerFullName("name");
        orderInputDto.setCustomerEmail("email");
        orderInputDto.setOrderItems(Sets.newHashSet());
        MarketplaceOrder order1 = new MarketplaceOrder();
        when(marketplaceOrderRepository.saveAndFlush(order1)).thenReturn(order1);
        assertEquals(order1, marketplaceOrderService.create(orderInputDto));
    }

    @Test
    void deleteShouldCallMethodOneTimeWhenOrderFound() {
        MarketplaceOrder order1 = new MarketplaceOrder();
        order1.setId(1);
        when(marketplaceOrderRepository.findById(anyInt())).thenReturn(Optional.of(order1));
        marketplaceOrderService.delete(1);
        verify(marketplaceOrderRepository, times(1)).deleteById(1);
    }

    @Test
    void deleteShouldThrowUpdateOrderDataExceptionWhenOrderWithSuccessStatus() {
        MarketplaceOrder order1 = new MarketplaceOrder();
        order1.setId(1);
        order1.setOrderStatus(OrderStatus.SUCCESS);
        when(marketplaceOrderRepository.findById(anyInt())).thenReturn(Optional.of(order1));
        assertThrows(UpdateOrderDataException.class, () -> {
            marketplaceOrderService.delete(1);
        });

    }

    @Test
    void deleteShouldThrowOrderNotFoundExceptionWhenOrderNotFound() {
        MarketplaceOrder order1 = new MarketplaceOrder();
        order1.setId(1);
        assertThrows(OrderNotFoundException.class, () -> {
            marketplaceOrderService.delete(1);
        });
    }

    private List<MarketplaceOrder> createMarketplaceOrdersList() {
        MarketplaceOrder order1 = new MarketplaceOrder();
        order1.setId(1);
        MarketplaceOrder order2 = new MarketplaceOrder();
        order1.setId(2);
        return Stream.of(order1, order2).collect(Collectors.toList());
    }
}
