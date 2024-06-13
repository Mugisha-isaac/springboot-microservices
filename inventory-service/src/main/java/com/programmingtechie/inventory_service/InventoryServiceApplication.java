package com.programmingtechie.inventory_service;

import com.programmingtechie.inventory_service.Repository.IInventoryRepository;
import com.programmingtechie.inventory_service.model.Inventory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class InventoryServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(InventoryServiceApplication.class, args);
	}

	@Bean
	public CommandLineRunner loadData(IInventoryRepository inventoryRepository){
       return (args) -> {
		   Inventory inventory = new Inventory();

		   inventory.setSkuCode("iphone_13");
		   inventory.setQuantity(100);

		   Inventory inventory1 = new Inventory();
		   inventory1.setSkuCode("iphone_13_red");
		   inventory1.setQuantity(0);

		   inventoryRepository.save(inventory);
		   inventoryRepository.save(inventory1);
		};
	}
}
