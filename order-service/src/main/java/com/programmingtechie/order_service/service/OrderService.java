package com.programmingtechie.order_service.service;

import com.programmingtechie.order_service.DTO.OrderLineItemsDto;
import com.programmingtechie.order_service.DTO.OrderRequest;
import com.programmingtechie.order_service.model.Order;
import com.programmingtechie.order_service.model.OrderLineItems;
import com.programmingtechie.order_service.repository.IOrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final IOrderRepository orderRepository;
    public void placeOrder(OrderRequest orderRequest){
        Order order = new Order();
        order.setOrderNumber(UUID.randomUUID().toString());
        List<OrderLineItems> orderLineItems = orderRequest.getOrderLineItemsDtoList().stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
        order.setOrderLineItems(orderLineItems);
        orderRepository.save(order);
    }

    private OrderLineItems mapToDto(OrderLineItemsDto orderLineItemsDto){
        OrderLineItems orderLineItems = new OrderLineItems();
        orderLineItems.setPrice(orderLineItemsDto.getPrice());
        orderLineItems.setQuantity(orderLineItemsDto.getQuantity());
        orderLineItems.setSkuCode(orderLineItemsDto.getSkuCode());
        return orderLineItems;
    }
}
