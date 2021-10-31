package com.servicemanagement.service;

import com.servicemanagement.domain.Order;
import com.servicemanagement.domain.User;
import com.servicemanagement.domain.enums.Status;
import com.servicemanagement.dto.OrderDTO;

import java.util.List;

public interface OrderService {

    /**
     * This method should create a new service
     *
     * @param dto
     * @return
     */
    Order createNewService(OrderDTO dto);

    /**
     * This method should find all user's services.
     *
     * @param user
     * @return
     */
    List<OrderDTO> findServicesByUser(User user);

    /**
     * This method should delete the service by its id.
     *
     * @param id
     */
    void deleteServiceById(Long id);

    /**
     * This method should update the service.
     *
     * @param dto
     */
    void updateService(OrderDTO dto);

    /**
     * This method should update the service status.
     *
     * @param id
     * @param status
     */
    void updateStatus(Long id, Status status);

}
