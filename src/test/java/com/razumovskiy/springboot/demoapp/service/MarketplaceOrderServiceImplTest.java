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

    @Mock
    private MarketplaceOrderConverter marketplaceOrderConverter;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void findAllShouldReturnListWithAllOrders() {
        //given
        MarketplaceOrder order1 = new MarketplaceOrder();
        order1.setId(1);
        MarketplaceOrder order2 = new MarketplaceOrder();
        order1.setId(2);
        when(marketplaceOrderRepository.findAll()).thenReturn(Stream.of(order1, order2).collect(Collectors.toList()));
        //when
        List<MarketplaceOrder> all = marketplaceOrderService.findAll();
        //then
        assertEquals(2, all.size());
    }

    @Test
    void findAllShouldReturnEmptyListWhenNoOrders() {
        //given
        when(marketplaceOrderRepository.findAll()).thenReturn(Collections.emptyList());
        //when
        List<MarketplaceOrder> all = marketplaceOrderService.findAll();
        //then
        assertEquals(0, all.size());
    }

    @Test
    void findOneShouldReturnOrderWhenFound() {
        //given
        MarketplaceOrder order1 = new MarketplaceOrder();
        order1.setId(1);
        when(marketplaceOrderRepository.findById(anyInt())).thenReturn(Optional.of(order1));
        //when
        MarketplaceOrder orderFound = marketplaceOrderService.findOne(1);
        //then
        assertEquals(order1, orderFound);
        assertNotNull(orderFound);
    }

    @Test
    void findOneShouldThrowOrderNotFoundExceptionWhenNotFound() {
        //given
        MarketplaceOrder order1 = new MarketplaceOrder();
        order1.setId(1);
        when(marketplaceOrderRepository.findById(anyInt())).thenReturn(Optional.of(order1));
        //when
        Integer orderId = null;
        //then
        assertThrows(OrderNotFoundException.class, () -> {
            marketplaceOrderService.findOne(orderId);
        });
    }

    @Test
    @Disabled
    void createShouldReturnOrderWhenSaved() {
        //given
        OrderInputDto orderInputDto = new OrderInputDto();
        orderInputDto.setCurrency("EUR");
        orderInputDto.setCustomerFullName("name");
        orderInputDto.setCustomerEmail("email");
        orderInputDto.setOrderItems(Sets.newHashSet());
        MarketplaceOrder order = new MarketplaceOrder();
        order.setId(1);
        order.setCustomerFullName("name");
        order.setOrderItems(Collections.emptyList());
        when(marketplaceOrderConverter.convertDtoToOrder(orderInputDto, order)).thenReturn(order);
        when(marketplaceOrderRepository.saveAndFlush(order)).thenReturn(order);
        //when
        MarketplaceOrder createdOrder = marketplaceOrderService.create(orderInputDto);
        //then
        assertEquals(order, createdOrder);
    }

    @Test
    void deleteShouldCallMethodOneTimeWhenOrderFound() {
        //given
        MarketplaceOrder order1 = new MarketplaceOrder();
        order1.setId(1);
        when(marketplaceOrderRepository.findById(anyInt())).thenReturn(Optional.of(order1));
        //when
        marketplaceOrderService.delete(1);
        //then
        verify(marketplaceOrderRepository, times(1)).deleteById(1);
    }

    @Test
    void deleteShouldThrowUpdateOrderDataExceptionWhenOrderWithSuccessStatus() {
        //given
        MarketplaceOrder order1 = new MarketplaceOrder();
        Integer orderId = 1;
        order1.setId(orderId);
        when(marketplaceOrderRepository.findById(anyInt())).thenReturn(Optional.of(order1));
        //when
        order1.setOrderStatus(OrderStatus.SUCCESS);
        //then
        assertThrows(UpdateOrderDataException.class, () -> {
            marketplaceOrderService.delete(orderId);
        });

    }

    @Test
    void deleteShouldThrowOrderNotFoundExceptionWhenOrderNotFound() {
        //given
        MarketplaceOrder order1 = new MarketplaceOrder();
        order1.setId(1);
        //when
        Integer orderId = 1;
        //then
        assertThrows(OrderNotFoundException.class, () -> {
            marketplaceOrderService.delete(orderId);
        });
    }
}
