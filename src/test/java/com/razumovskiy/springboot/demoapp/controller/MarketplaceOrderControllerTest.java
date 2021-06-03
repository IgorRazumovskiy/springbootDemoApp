package com.razumovskiy.springboot.demoapp.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.razumovskiy.springboot.demoapp.db.MarketplaceOrderRepository;
import com.razumovskiy.springboot.demoapp.dto.OrderInputDto;
import com.razumovskiy.springboot.demoapp.model.MarketplaceOrder;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@RunWith(SpringRunner.class)
@SpringBootTest
@TestPropertySource("/application-test.properties")
@Sql(value={"/data-test.sql"})
class MarketplaceOrderControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private MarketplaceOrderRepository marketplaceOrderRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void getAllShouldReturnOrderListWhenRequestIsOk() throws Exception {
        mockMvc.perform(get("/orders"))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print());
        assertThat(marketplaceOrderRepository.count()).isEqualTo(3);
    }

    @Test
    void getOrderByIdShouldReturnCorrectOrderWhenOrderFound() throws Exception {
        Integer orderId = 11;
        MarketplaceOrder order = new MarketplaceOrder();
        order.setCustomerFullName("Norbert Jahns");
        mockMvc.perform(get("/orders/{orderId}", orderId))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print());
        assertThat(marketplaceOrderRepository.findById(orderId).get().getCustomerFullName()).isEqualTo(order.getCustomerFullName());
    }

    @Test
    void getOrderByIdShouldReturnNotFoundStatusWhenOrderNotFound() throws Exception {
        Integer orderId = 20;
        mockMvc.perform(get("/orders/{orderId}", orderId))
                .andExpect(content().string("Order not found for Id " +  orderId))
                .andExpect(status().isNotFound())
                .andDo(print());
    }

    @Test
    void createShouldReturnCreatedStatusWhenOrderIsSaved() throws Exception {
        assertThat(marketplaceOrderRepository.count()).isEqualTo(3);
        OrderInputDto dto = new OrderInputDto();
        dto.setCurrency("EUR");
        dto.setCustomerFullName("Angel Jordanescu");
        dto.setCustomerEmail("angel@post.com");
        dto.setOrderItems(Collections.emptySet());

        mockMvc.perform(post("/orders")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dto))
                .characterEncoding("utf-8"))
                .andExpect(status().isCreated())
                .andDo(print());
        assertThat(marketplaceOrderRepository.count()).isEqualTo(4);
        assertThat(marketplaceOrderRepository.findById(14).get().getCustomerFullName()).isEqualTo(dto.getCustomerFullName());
    }

    @Test
    void updateShouldReturnOkWhenOrderUpdated() throws Exception {
        Integer orderId = 13;
        OrderInputDto dto = new OrderInputDto();
        dto.setId(orderId);
        dto.setCurrency("USD");
        dto.setCustomerFullName("Akram Sekkari");
        dto.setCustomerEmail("new_mail@server.de");
        dto.setOrderItems(Collections.emptySet());
        mockMvc.perform(put("/orders")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dto))
                .characterEncoding("utf-8"))
                .andExpect(status().isOk())
                .andDo(print());
        assertThat(marketplaceOrderRepository.findById(orderId).get().getCustomerEmail()).isEqualTo(dto.getCustomerEmail());
    }

    @Test
    void updateShouldReturnBadRequestStatusWhenOrderImpossibleToUpdate() throws Exception {
        Integer orderId = 11;
        OrderInputDto dto = new OrderInputDto();
        dto.setId(orderId);
        dto.setCurrency("EUR");
        dto.setCustomerFullName("Norbert Jahns");
        dto.setCustomerEmail("norbert.jahns@server.de");
        dto.setOrderItems(Collections.emptySet());
        mockMvc.perform(put("/orders")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dto))
                .characterEncoding("utf-8"))
                .andExpect(content().string("Order with such status can't be updated! Id=" + orderId))
                .andExpect(status().isBadRequest())
                .andDo(print());
    }

    @Test
    void deleteShouldReturnNotFoundStatusWhenOrderNotFound() throws Exception {
        Integer orderId = 20;
        mockMvc.perform(delete("/orders/{orderId}", orderId))
                .andExpect(content().string("Order not found for Id " +  orderId))
                .andExpect(status().isNotFound())
                .andDo(print());
    }

    @Test
    void deleteShouldReturnBadRequestStatusWhenOrderImpossibleToDelete() throws Exception {
        Integer orderId = 11;
        mockMvc.perform(delete("/orders/{orderId}", orderId))
                .andExpect(content().string("Order can't be removed with SUCCESS status! Id=" + orderId))
                .andExpect(status().isBadRequest())
                .andDo(print());
    }

    @Test
    void deleteShouldSuccessRemoveWhenOrderAllowedToDelete() throws Exception {
        Integer orderId = 12;
        mockMvc.perform(delete("/orders/{orderId}", orderId))
                .andExpect(content().string("Order removed successfully. Id=" + orderId))
                .andExpect(status().isOk())
                .andDo(print());
    }
}