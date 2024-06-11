package com.programmingtechie.order_service.repository;

import com.programmingtechie.order_service.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IOrderRepository extends JpaRepository<Order,Long> {
}
