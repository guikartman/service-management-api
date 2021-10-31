package com.servicemanagement.service;

import com.servicemanagement.domain.Order;
import com.servicemanagement.domain.User;
import com.servicemanagement.domain.enums.Status;
import com.servicemanagement.dto.OrderDTO;
import com.servicemanagement.repository.OrderRepository;
import com.servicemanagement.service.exceptions.OrderNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class OrderServiceImpl implements OrderService {

    private final OrderRepository repository;
    private final S3Service s3Service;

    public OrderServiceImpl(OrderRepository repository, S3Service s3Service) {
        this.repository = repository;
        this.s3Service = s3Service;
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
        String imageUrl = order.getImageUrl();
        repository.delete(order);
        deleteOldImageFromS3(imageUrl);
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
        String oldImageUrl = updateImage(order, dto);
        repository.save(order);
        deleteOldImageFromS3(oldImageUrl);
    }

    private void deleteOldImageFromS3(String oldImage) {
        if (Objects.nonNull(oldImage) && oldImage.length() > 0) {
            String[] splitUrl = oldImage.split("/");
            String keyName = splitUrl[splitUrl.length - 1];
            s3Service.deleteFile(keyName);
        }
    }

    /**
     *
     * Update the image ulr and return the old link.
     *
     * @param order
     * @param orderDTO
     * @return
     */
    private String updateImage(Order order, OrderDTO orderDTO) {
        String oldUrl = null;
        if (Objects.isNull(order.getImageUrl()) && Objects.nonNull(orderDTO.getImageUrl())) {
            order.setImageUrl(orderDTO.getImageUrl());
        } else if(!Objects.equals(order.getImageUrl(), orderDTO.getImageUrl())) {
            oldUrl = order.getImageUrl();
            order.setImageUrl(orderDTO.getImageUrl());
        }
        return oldUrl;
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
