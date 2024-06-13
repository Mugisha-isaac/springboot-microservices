package com.programmingtechie.order_service.clients;

import com.programmingtechie.order_service.DTO.InventoryResponse;
import com.programmingtechie.order_service.config.FeignClientConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(name = "inventory-service", configuration = FeignClientConfig.class)
public interface InventoryFeignClient {
    @GetMapping(value = "/api/inventory", consumes = MediaType.APPLICATION_JSON_VALUE)
    List<InventoryResponse> isInStock(@RequestParam List<String> skuCodes);
}
