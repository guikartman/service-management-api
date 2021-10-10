package com.servicemanagement.service;

import com.servicemanagement.domain.Cellphone;
import com.servicemanagement.domain.Customer;
import com.servicemanagement.domain.User;
import com.servicemanagement.dto.CustomerDTO;
import com.servicemanagement.repository.CustomerRepository;
import com.servicemanagement.service.exceptions.CustomerNotFoundException;
import com.servicemanagement.service.exceptions.UserAlreadyPresentException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

class CustomerServiceImplTest {

    private CustomerServiceImpl service;

    @Mock
    private CustomerRepository customerRepository;

    private User user;
    private Customer customer;
    private Cellphone cellphone;

    @BeforeEach
    public void init() {
        MockitoAnnotations.openMocks(this);
        this.service = new CustomerServiceImpl(customerRepository);
        this.user = new User("test","test@gmail.com","test");
        this.cellphone = new Cellphone(61, 999999999, true, customer);
    }

    @Test
    void createNewCustomerTest() {
        CustomerDTO customerDTO = new CustomerDTO( null,"testc@gmail.com", "Customer Test", cellphone);
        service.createNewCustomer(customerDTO, user);
        verify(customerRepository, times(1)).save(any(Customer.class));
    }

    @Test
    void getCustomersByUserTest() {
        Customer customer1 = new Customer("customer1","c1@gmail.com",user, cellphone);
        Customer customer2 = new Customer("customer2","c2@gmail.com",user, cellphone);
        when(customerRepository.findCustomersByUser(user)).thenReturn(Arrays.asList(customer1,customer2));
        List<CustomerDTO> customers = service.getCustomersByUser(user);
        assertThat(customers).isNotNull();
        assertThat(customers.size()).isEqualTo(2);
    }

    @Test
    void updateCustomerTest() {
        Customer testCustomer = new Customer(1L,"test@gmail.com","Test", user, cellphone);
        when(customerRepository.findById(1L)).thenReturn(Optional.of(testCustomer));
        CustomerDTO customerDTO = new CustomerDTO(1L,"test@gmail.com","Test", cellphone);
        service.updateCustomer(customerDTO);
        verify(customerRepository, times(1)).save(any(Customer.class));
    }

    @Test
    void deleteCustomerTest() {
        Customer testCustomer = new Customer(1L,"test@gmail.com","Test", user, cellphone);
        when(customerRepository.findById(1L)).thenReturn(Optional.of(testCustomer));
        service.deleteCustomerById(1L);
        verify(customerRepository,times(1)).delete(testCustomer);
    }

    @Test
    void getCustomerByIdTest() {
        Customer testCustomer = new Customer(1L,"test@gmail.com","Test", user, cellphone);
        when(customerRepository.findById(1L)).thenReturn(Optional.of(testCustomer));
        Customer actual = service.getCustomerById(1L);
        assertThat(actual).isEqualTo(testCustomer);
    }

    @Test
    void getCustomerByIdExceptionTest() {
        assertThrows(CustomerNotFoundException.class, () -> service.getCustomerById(1L));
    }
}