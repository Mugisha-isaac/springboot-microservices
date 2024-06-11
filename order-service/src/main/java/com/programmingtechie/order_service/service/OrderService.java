package com.programmingtechie.order_service.service;

import com.programmingtechie.order_service.DTO.OrderLineItemsDto;
import com.programmingtechie.order_service.DTO.OrderRequest;
import com.programmingtechie.order_service.config.WebClientConfig;
import com.programmingtechie.order_service.model.Order;
import com.programmingtechie.order_service.model.OrderLineItems;
import com.programmingtechie.order_service.repository.IOrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final IOrderRepository orderRepository;
    private final WebClient webClient;

    public void placeOrder(OrderRequest orderRequest) {
        Order order = new Order();
        order.setOrderNumber(UUID.randomUUID().toString());
        List<OrderLineItems> orderLineItems = orderRequest.getOrderLineItemsDtoList().stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
        order.setOrderLineItems(orderLineItems);

        // call inventory service and place order if product is in stock
       Boolean result = webClient.get()
                .uri("http://localhost:8081/api/inventory")
                .retrieve()
                .bodyToMono(Boolean.class)
                .block();

       if(Boolean.TRUE.equals(result)){
           orderRepository.save(order);
       } else {
           throw new IllegalArgumentException("Product is out of stock");
       }
    }

    private OrderLineItems mapToDto(OrderLineItemsDto orderLineItemsDto) {
        OrderLineItems orderLineItems = new OrderLineItems();
        orderLineItems.setPrice(orderLineItemsDto.getPrice());
        orderLineItems.setQuantity(orderLineItemsDto.getQuantity());
        orderLineItems.setSkuCode(orderLineItemsDto.getSkuCode());
        return orderLineItems;
    }
}
