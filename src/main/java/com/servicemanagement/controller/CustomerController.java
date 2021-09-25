package com.servicemanagement.controller;

import com.servicemanagement.domain.Customer;
import com.servicemanagement.domain.User;
import com.servicemanagement.dto.CustomerDTO;
import com.servicemanagement.service.CustomerService;
import com.servicemanagement.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/customers")
public class CustomerController {

    private UserService userService;
    private CustomerService customerService;

    public CustomerController(UserService userService, CustomerService customerService) {
        this.userService = userService;
        this.customerService = customerService;
    }

    @GetMapping
    public ResponseEntity<List<CustomerDTO>> retrieveCustomersByUser(@RequestParam(value = "email") String email) {
        User user = userService.findUserByEmail(email);
        List<CustomerDTO> list = customerService.getCustomersByUser(user);
        return ResponseEntity.ok(list);
    }

    @PostMapping
    public ResponseEntity<Void> createNewCustomer(@RequestParam(value = "email") String email, @Valid @RequestBody CustomerDTO customerDto) {
        User user = userService.findUserByEmail(email);
        Customer newCustomer = customerService.createNewCustomer(customerDto, user);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(newCustomer.getId())
                .toUri();
        return ResponseEntity.created(uri).build();
    }

    @PutMapping
    public ResponseEntity<Void> updateCustomer(@Valid @RequestBody CustomerDTO customerDTO) {
        customerService.updateCustomer(customerDTO);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteCustomerById(@RequestParam(value = "id") Long id) {
        customerService.deleteCustomerById(id);
        return ResponseEntity.noContent().build();
    }

}
