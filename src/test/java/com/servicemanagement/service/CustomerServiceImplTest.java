package com.servicemanagement.service;

import com.servicemanagement.domain.Cellphone;
import com.servicemanagement.domain.Customer;
import com.servicemanagement.domain.User;
import com.servicemanagement.dto.CustomerDTO;
import com.servicemanagement.dto.CustomerNewDTO;
import com.servicemanagement.repository.CustomerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

class CustomerServiceImplTest {

    private CustomerServiceImpl service;

    @Mock
    private CustomerRepository customerRepository;

    private User user;
    private Customer customer;
    private Set<Cellphone> cellphone = new HashSet<>();

    @BeforeEach
    public void init() {
        MockitoAnnotations.openMocks(this);
        this.service = new CustomerServiceImpl(customerRepository);
        this.user = new User("test","test@gmail.com","test");
        cellphone.add(new Cellphone(61, 999999999,true));
        cellphone.add(new Cellphone(61, 888888888,false));
    }

    @Test
    public void createNewCustomerTest() {
        CustomerNewDTO customerDTO = new CustomerNewDTO("testc@gmail.com", "Customer Test", cellphone);
        service.createNewCustomer(customerDTO, user);
        verify(customerRepository, times(1)).save(any(Customer.class));
    }

    @Test
    public void getCustomersByUserTest() {
        Customer customer1 = new Customer("customer1","c1@gmail.com",user);
        Customer customer2 = new Customer("customer2","c2@gmail.com",user);
        when(customerRepository.findCustomersByUser(user)).thenReturn(Arrays.asList(customer1,customer2));
        List<CustomerDTO> customers = service.getCustomersByUser(user);
        assertThat(customers).isNotNull();
        assertThat(customers.size()).isEqualTo(2);
    }
}