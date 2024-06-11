package com.programmingtechie.order_service.controller;

import com.programmingtechie.order_service.DTO.OrderRequest;
import com.programmingtechie.order_service.model.Order;
import com.programmingtechie.order_service.service.OrderService;
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
    public String placeOrder(@RequestBody OrderRequest orderRequest) {
         orderService.placeOrder(orderRequest);
         return "Order Placed Successfully";
    }
}
