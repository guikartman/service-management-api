package com.servicemanagement.controller;

import com.servicemanagement.domain.Customer;
import com.servicemanagement.domain.Order;
import com.servicemanagement.domain.User;
import com.servicemanagement.dto.OrderDTO;
import com.servicemanagement.service.CustomerService;
import com.servicemanagement.service.OrderService;
import com.servicemanagement.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/orders")
public class OrderController {

    private final OrderService orderService;
    private final CustomerService customerService;
    private final UserService userService;

    public OrderController(OrderService orderService, CustomerService customerService,UserService userService) {
        this.orderService = orderService;
        this.customerService = customerService;
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<Void> createNewOrder(@RequestParam(value = "email") String email,@RequestParam(value = "customerId") Long customerId, @Valid @RequestBody OrderDTO orderDTO) {
        User user = userService.findUserByEmail(email);
        Customer customer = customerService.getCustomerById(customerId);
        orderDTO.setUser(user);
        orderDTO.setCustomer(customer);
        Order order = orderService.createNewService(orderDTO);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}").buildAndExpand(order.getId()).toUri();
        return ResponseEntity.created(uri).build();
    }

    @GetMapping
    public ResponseEntity<List<OrderDTO>> getUserOrdersByEmail(@RequestParam(value = "email") String email) {
        User user = userService.findUserByEmail(email);
        List<OrderDTO> ordersDTO = orderService.findServicesByUser(user);
        return ResponseEntity.ok(ordersDTO);
    }
}
