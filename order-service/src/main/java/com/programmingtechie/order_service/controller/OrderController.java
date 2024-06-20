package com.programmingtechie.order_service.controller;

import com.programmingtechie.order_service.DTO.OrderRequest;
import com.programmingtechie.order_service.model.Order;
import com.programmingtechie.order_service.service.OrderService;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @CircuitBreaker(name = "inventory", fallbackMethod = "placeOrderFallback")
    public String placeOrder(@RequestBody OrderRequest orderRequest) {
         orderService.placeOrder(orderRequest);
         return "Order Placed Successfully";
    }

    public String placeOrderFallback(OrderRequest orderRequest, RuntimeException e) {
        return "Order Service is down. Please try again later";
    }
}
