package com.servicemanagement.repository;

import com.servicemanagement.domain.Order;
import com.servicemanagement.domain.User;
import com.servicemanagement.domain.enums.Status;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findByUserOrderByDeliveryDate(User user);
}
