package com.servicemanagement.service;

import com.servicemanagement.domain.Order;
import com.servicemanagement.domain.User;
import com.servicemanagement.domain.enums.Status;
import com.servicemanagement.dto.OrderDTO;
import com.servicemanagement.repository.OrderRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderServiceImpl implements OrderService {

    private final OrderRepository repository;

    public OrderServiceImpl(OrderRepository repository) {
        this.repository = repository;
    }

    @Override
    public Order createNewService(OrderDTO dto) {
        Order order = orderFromDTO(dto);
        return repository.save(order);
    }

    @Override
    public List<OrderDTO> findServicesByUser(User user) {
        List<OrderDTO> ordersDTO = repository.findByUser(user).stream().map((order) -> new OrderDTO(order)).collect(Collectors.toList());
        return ordersDTO;
    }

    private Order orderFromDTO(OrderDTO dto) {
        Status status = dto.getStatus();
        if (status == null) {
            status = Status.OPEN;
        }
        Boolean isPayed = dto.getIsPayed();
        if (isPayed == null) {
            isPayed = false;
        }
        return new Order(
                dto.getId(),
                dto.getUser(),
                dto.getCustomer(),
                dto.getTitle(),
                dto.getStartDate(),
                dto.getDeliveryDate(),
                dto.getDescription(),
                dto.getPrice(),
                status,
                isPayed);
    }
}
