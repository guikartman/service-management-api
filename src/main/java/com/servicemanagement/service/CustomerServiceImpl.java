package com.servicemanagement.service;

import com.servicemanagement.domain.Customer;
import com.servicemanagement.domain.User;
import com.servicemanagement.dto.CustomerDTO;
import com.servicemanagement.repository.CustomerRepository;
import com.servicemanagement.service.exceptions.CustomerNotFoundException;
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
    public Customer createNewCustomer(CustomerDTO customerNewDTO, User user) {
        Customer customer = new Customer(customerNewDTO.getEmail(), customerNewDTO.getName(),user, customerNewDTO.getCellphone());
        return repository.save(customer);
    }

    @Override
    public void updateCustomer(CustomerDTO customer) {
        Customer customerEntity = repository.findById(customer.getId()).orElseThrow(() -> new CustomerNotFoundException(customer.getId()));
        customerEntity.setEmail(customer.getEmail());
        customerEntity.setName(customer.getName());
        customerEntity.getCellphone().setDdd(customer.getCellphone().getDdd());
        customerEntity.getCellphone().setCellphoneNumber(customer.getCellphone().getCellphoneNumber());
        customerEntity.getCellphone().setIsWhatsapp(customer.getCellphone().getIsWhatsapp());
        repository.save(customerEntity);
    }

    @Override
    public void deleteCustomerById(Long id) {
        Customer customer = repository.findById(id).orElseThrow(() -> new CustomerNotFoundException(id));
        repository.delete(customer);
    }
}
