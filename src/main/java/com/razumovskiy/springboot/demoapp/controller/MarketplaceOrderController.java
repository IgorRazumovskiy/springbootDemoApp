package com.razumovskiy.springboot.demoapp.controller;

import com.razumovskiy.springboot.demoapp.dto.OrderInputDto;
import com.razumovskiy.springboot.demoapp.model.MarketplaceOrder;
import com.razumovskiy.springboot.demoapp.service.MarketplaceOrderService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controller for {@link MarketplaceOrder MarketplaceOrder} CRUD methods
 *
 * @author Igor Razumovskiy
 * @version 1.0
 * @since 03/06/21
 */
@Api(value = "REST API for MarketplaceOrder")
@RestController
@RequestMapping("/orders")
public class MarketplaceOrderController {

    @Autowired
    private MarketplaceOrderService marketplaceOrderService;

    @ApiOperation(value = "get list of all MarketplaceOrders")
    @GetMapping
    public ResponseEntity<List<MarketplaceOrder>> getAll() {
        return ResponseEntity.ok(marketplaceOrderService.findAll());
    }

    @ApiOperation(value = "get MarketplaceOrder by id", response = MarketplaceOrder.class)
    @GetMapping("{id}")
    public ResponseEntity<MarketplaceOrder> getOrderById(@PathVariable Integer id) {
        return ResponseEntity.ok(marketplaceOrderService.findOne(id));
    }

    @ApiOperation(value = "save new MarketplaceOrder")
    @ApiResponse(code = 201, message = "CREATED", response = MarketplaceOrder.class)
    @PostMapping
    public ResponseEntity<MarketplaceOrder> createOrder(@RequestBody OrderInputDto orderInputDto) {
        return new ResponseEntity<>(marketplaceOrderService.create(orderInputDto), HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity<MarketplaceOrder> updateOrder(@RequestBody OrderInputDto orderInputDto) {
        return ResponseEntity.ok(marketplaceOrderService.update(orderInputDto));
    }

    @ApiOperation(value = "remove MarketplaceOrder by id", response = String.class)
    @DeleteMapping("{id}")
    public ResponseEntity<String> deleteOrder(@PathVariable Integer id) {
        marketplaceOrderService.delete(id);
        return ResponseEntity.ok("Order removed successfully. Id=" + id);
    }
}
