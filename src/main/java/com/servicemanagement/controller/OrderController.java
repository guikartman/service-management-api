package com.servicemanagement.controller;

import com.servicemanagement.domain.User;
import com.servicemanagement.domain.enums.Status;
import com.servicemanagement.dto.OrderDTO;
import com.servicemanagement.service.OrderService;
import com.servicemanagement.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/orders")
public class OrderController {

    private final OrderService orderService;
    private final UserService userService;

    public OrderController(OrderService orderService, UserService userService) {
        this.orderService = orderService;
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<Void> createNewOrder(@RequestParam(value = "email") String email, @Valid @RequestBody OrderDTO orderDTO) {
        var user = userService.findUserByEmail(email);
        orderDTO.setUser(user);
        var order = orderService.createNewService(orderDTO);
        var uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}").buildAndExpand(order.getId()).toUri();
        return ResponseEntity.created(uri).build();
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> deleteOrder(@PathVariable(value = "id") Long id) {
        orderService.deleteServiceById(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping
    public ResponseEntity<Void> updateOrder(@Valid @RequestBody OrderDTO orderDTO) {
        orderService.updateService(orderDTO);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping(value = "/{id}")
    public ResponseEntity<Void> updateStatus(@PathVariable(value = "id") Long id, @RequestParam(value = "status") String status) {
        orderService.updateStatus(id, Status.valueOf(status));
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<List<OrderDTO>> getUserOrdersByEmail(@RequestParam(value = "email") String email) {
        User user = userService.findUserByEmail(email);
        List<OrderDTO> ordersDTO = orderService.findServicesByUser(user);
        return ResponseEntity.ok(ordersDTO);
    }
}
