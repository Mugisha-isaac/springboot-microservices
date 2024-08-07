package com.programmingtechie.order_service.service;

import com.programmingtechie.order_service.DTO.InventoryResponse;
import com.programmingtechie.order_service.DTO.OrderLineItemsDto;
import com.programmingtechie.order_service.DTO.OrderRequest;
import com.programmingtechie.order_service.clients.InventoryFeignClient;
import com.programmingtechie.order_service.config.WebClientConfig;
import com.programmingtechie.order_service.event.OrderPlacedEvent;
import com.programmingtechie.order_service.model.Order;
import com.programmingtechie.order_service.model.OrderLineItems;
import com.programmingtechie.order_service.repository.IOrderRepository;
import com.programmingtechie.order_service.utils.MapperUtil;
import io.micrometer.tracing.Span;
import io.micrometer.tracing.Tracer;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final IOrderRepository orderRepository;
    private final WebClient.Builder webClientBuilder;
    private final Tracer tracer;
    private final KafkaTemplate<String,OrderPlacedEvent> kafkaTemplate;
//    private final InventoryFeignClient inventoryFeignClient;

    public String placeOrder(OrderRequest orderRequest) {
        Order order = new Order();
        order.setOrderNumber(UUID.randomUUID().toString());
        List<OrderLineItems> orderLineItems = orderRequest.getOrderLineItemsDtoList().stream()
                .map(dto -> MapperUtil.mapToDto(dto, OrderLineItems.class))
                .collect(Collectors.toList());
        order.setOrderLineItems(orderLineItems);

        List<String> skuCodes = orderLineItems.stream()
                .map(OrderLineItems::getSkuCode)
                .toList();

       Span InventoryServiceLookUp =  tracer.nextSpan().name("InventoryServiceLookUp");
       try(Tracer.SpanInScope spanInScope = tracer.withSpan(InventoryServiceLookUp.start())){
           // call inventory service and place order if product is in stock
           InventoryResponse[] inventoryResponsesArray = webClientBuilder.build().get()
                   .uri("http://inventory-service/api/inventory",
                           uriBuilder -> uriBuilder.queryParam("skuCodes", skuCodes)
                                   .build())
                   .retrieve()
                   .bodyToMono(InventoryResponse[].class)
                   .block();

//        List<InventoryResponse> inventoryResponsesArray = inventoryFeignClient.isInStock(skuCodes);

           System.out.println("inventoryResponsesArray = " + Arrays.toString(inventoryResponsesArray));
           boolean allProductsInStock = Arrays.stream(Objects.requireNonNull(inventoryResponsesArray)).allMatch(InventoryResponse::getIsInStock);
//        boolean allProductsInStock = inventoryResponsesArray.stream().allMatch(InventoryResponse::getIsInStock);
           if (allProductsInStock) {
               orderRepository.save(order);
               kafkaTemplate.send("notificationTopic", new OrderPlacedEvent(order.getOrderNumber()));
               return "Order Placed Successfully!";
           } else {
               throw new IllegalArgumentException("Product is out of stock");
           }

       } finally {
              InventoryServiceLookUp.end();
       }

    }
}
