package com.servicemanagement.service;

import com.servicemanagement.domain.Customer;
import com.servicemanagement.domain.User;
import com.servicemanagement.dto.CustomerDTO;
import com.servicemanagement.dto.CustomerNewDTO;
import com.servicemanagement.repository.CustomerRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CustomerServiceImpl implements CustomerService{

    private CustomerRepository repository;

    public CustomerServiceImpl(CustomerRepository repository){
        this.repository = repository;
    }

    @Override
    public List<CustomerDTO> getCustomersByUser(User user) {
        List<Customer> customers = repository.findCustomersByUser(user);
        List<CustomerDTO> customersDTO = customers.stream().map(cust -> new CustomerDTO(cust)).collect(Collectors.toList());
        return customersDTO;
    }

    @Override
    public Customer createNewCustomer(CustomerNewDTO customerNewDTO, User user) {
        Customer customer = new Customer(customerNewDTO.getEmail(), customerNewDTO.getName(),user);
        customerNewDTO.getCellphones().forEach(cell -> {
            cell.setCustomer(customer);
            customer.getCellphones().add(cell);
        });
        return repository.save(customer);
    }
}
