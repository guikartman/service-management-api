package com.servicemanagement.service;

import com.servicemanagement.domain.Customer;
import com.servicemanagement.domain.Order;
import com.servicemanagement.domain.User;
import com.servicemanagement.dto.OrderDTO;
import com.servicemanagement.repository.OrderRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class OrderServiceImplTest {

    private OrderRepository repository = mock(OrderRepository.class);
    private Customer customerMocked = mock(Customer.class);
    private User userMocked = mock(User.class);
    private Order orderMocked = mock(Order.class);

    private OrderServiceImpl service;

    @BeforeEach
    public void initTest() {
        this.service = spy(new OrderServiceImpl(repository));
    }

    @Test
    void createNewServiceTest() {
        OrderDTO dto = new OrderDTO(
                "test",
                LocalDate.now(),
                LocalDate.of(2021,9,30),
                "Order test",
                BigDecimal.valueOf(25.50),
                customerMocked);
        service.createNewService(dto);
        verify(repository, times(1)).save(any(Order.class));
    }

    @Test
    void findServicesByUserTest() {
        when(repository.findByUser(userMocked)).thenReturn(Arrays.asList(orderMocked));
        List<OrderDTO> servicesByUser = service.findServicesByUser(userMocked);
        assertThat(servicesByUser.size()).isEqualTo(1);
    }

    @Test
    void findServicesByUserValidateContentTest() {
        when(orderMocked.getDescription()).thenReturn("Order test");
        when(repository.findByUser(userMocked)).thenReturn(Arrays.asList(orderMocked));
        List<OrderDTO> servicesByUser = service.findServicesByUser(userMocked);
        assertThat(servicesByUser.get(0).getDescription()).isEqualTo("Order test");
    }

    @Test
    void findServicesByUserEmptyTest() {
        List<OrderDTO> servicesByUser = service.findServicesByUser(userMocked);
        assertThat(servicesByUser.isEmpty()).isTrue();
    }
}