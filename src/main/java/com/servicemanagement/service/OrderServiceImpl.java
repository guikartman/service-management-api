package com.servicemanagement.service;

import com.servicemanagement.domain.Order;
import com.servicemanagement.domain.User;
import com.servicemanagement.domain.enums.Status;
import com.servicemanagement.dto.OrderDTO;
import com.servicemanagement.repository.OrderRepository;
import com.servicemanagement.service.exceptions.OrderNotFoundException;
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
        var order = orderFromDTO(dto);
        return repository.save(order);
    }

    @Override
    public List<OrderDTO> findServicesByUser(User user) {
        return repository.findByUserOrderByDeliveryDate(user).stream().map(OrderDTO::new).collect(Collectors.toList());
    }

    @Override
    public void deleteServiceById(Long id) {
        var order = repository.findById(id).orElseThrow(() -> new OrderNotFoundException(id));
        repository.delete(order);
    }

    @Override
    public void updateService(OrderDTO dto) {
        var order = repository.findById(dto.getId()).orElseThrow(() -> new OrderNotFoundException(dto.getId()));
        order.setTitle(dto.getTitle());
        order.setDescription(dto.getDescription());
        order.setDeliveryDate(dto.getDeliveryDate());
        order.setStartDate(dto.getStartDate());
        order.setPrice(dto.getPrice());
        order.setIsPayed(dto.getIsPayed());
        order.setCustomer(dto.getCustomer());
        order.setImageUrl(dto.getImageUrl());
        repository.save(order);
    }

    @Override
    public void updateStatus(Long id, Status status) {
        var order = repository.findById(id).orElseThrow(() -> new OrderNotFoundException(id));
        order.setStatus(status);
        repository.save(order);
    }

    private Order orderFromDTO(OrderDTO dto) {
        var status = dto.getStatus();
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
                isPayed,
                dto.getImageUrl());
    }
}
