package com.programmingtechie.inventory_service.service;

import com.programmingtechie.inventory_service.Repository.IInventoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class InventoryService {
    private final IInventoryRepository inventoryRepository;

    @Transactional(readOnly = true)
    public boolean isInStock(String skuCode) {
        return inventoryRepository.findBySkuCode(skuCode).isPresent();
    }
}
