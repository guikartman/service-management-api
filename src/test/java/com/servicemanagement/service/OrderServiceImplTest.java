package com.servicemanagement.service;

import com.servicemanagement.domain.Customer;
import com.servicemanagement.domain.Order;
import com.servicemanagement.domain.User;
import com.servicemanagement.domain.enums.Status;
import com.servicemanagement.dto.OrderDTO;
import com.servicemanagement.repository.OrderRepository;
import com.servicemanagement.service.exceptions.OrderNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.openMocks;

class OrderServiceImplTest {

    @Mock
    private OrderRepository repository;

    @Mock
    private Customer customerMocked;

    @Mock
    private User userMocked;

    @Mock
    private S3Service s3ServiceMock;

    @Mock
    private Order orderMocked;

    @Captor
    private ArgumentCaptor<Order> orderCaptor;

    private OrderServiceImpl service;

    @BeforeEach
    public void initTest() {
        openMocks(this);
        this.service = spy(new OrderServiceImpl(repository, s3ServiceMock));
    }

    @Test
    void createNewServiceTest() {
        OrderDTO dto = new OrderDTO(
                "test",
                LocalDate.now(),
                LocalDate.of(2021,9,30),
                "Order test",
                BigDecimal.valueOf(25.50),
                false,
                customerMocked,
                "dummy_url");
        service.createNewService(dto);
        verify(repository, times(1)).save(orderCaptor.capture());
        Order order = orderCaptor.getValue();
        assertThat(order).isNotNull();
    }

    @Test
    void createNewServiceNullTestsTest() {
        OrderDTO dto = new OrderDTO(
                "test",
                LocalDate.now(),
                LocalDate.of(2021,9,30),
                "Order test",
                BigDecimal.valueOf(25.50),
                null,
                customerMocked,
                "dummy_url");
        dto.setStatus(null);
        service.createNewService(dto);

        verify(repository, times(1)).save(orderCaptor.capture());

        Order order = orderCaptor.getValue();
        assertThat(order.getStatus()).isEqualTo(Status.OPEN);
        assertThat(order.getIsPayed()).isFalse();
    }

    @Test
    void deleteServiceByIdTest() {
        when(repository.findById(anyLong())).thenReturn(Optional.of(orderMocked));
        service.deleteServiceById(1L);
        verify(repository, times(1)).delete(orderCaptor.capture());
        assertThat(orderCaptor.getValue()).isEqualTo(orderMocked);
    }

    @Test
    void deleteServiceByIdWithImageTest() {
        when(orderMocked.getImageUrl()).thenReturn("dummy.url");
        when(repository.findById(anyLong())).thenReturn(Optional.of(orderMocked));
        service.deleteServiceById(1L);
        verify(s3ServiceMock, times(1)).deleteFile(anyString());
    }

    @Test
    void deleteServiceByIdExceptionTest() {
        assertThrows(OrderNotFoundException.class, () -> service.deleteServiceById(1L));
    }

    @Test
    void updateServiceTest() {
        when(repository.findById(anyLong())).thenReturn(Optional.of(orderMocked));
        OrderDTO dto = new OrderDTO(
                "test",
                LocalDate.now(),
                LocalDate.of(2021,9,30),
                "Order test",
                BigDecimal.valueOf(25.50),
                false,
                customerMocked,
                "dummy_url");
        dto.setId(1L);
        service.updateService(dto);
        verify(repository, times(1)).save(orderCaptor.capture());
        assertThat(orderCaptor.getValue()).isNotNull();
    }

    @Test
    void updateServiceWithNewImageTest() {
        final String oldImageUrl = "oldImageUrl";
        when(orderMocked.getImageUrl()).thenReturn(oldImageUrl);
        when(repository.findById(anyLong())).thenReturn(Optional.of(orderMocked));
        OrderDTO dto = new OrderDTO(
                "test",
                LocalDate.now(),
                LocalDate.of(2021,9,30),
                "Order test",
                BigDecimal.valueOf(25.50),
                false,
                customerMocked,
                "dummy_url");
        dto.setId(1L);
        service.updateService(dto);
        verify(s3ServiceMock, times(1)).deleteFile(oldImageUrl);
    }

    @Test
    void updateServiceExceptionTest() {
        OrderDTO dto = new OrderDTO();
        dto.setId(1L);
        assertThrows(OrderNotFoundException.class, () -> service.updateService(dto));
    }

    @Test
    void updateStatusTest() {
        Order order = new Order();
        when(repository.findById(anyLong())).thenReturn(Optional.of(order));
        service.updateStatus(1L, Status.COMPLETED);
        verify(repository, times(1)).save(orderCaptor.capture());
        assertThat(orderCaptor.getValue().getStatus()).isEqualTo(Status.COMPLETED);
    }

    @Test
    void updateStatusExceptionTest() {
        assertThrows(OrderNotFoundException.class, () -> service.updateStatus(1L, Status.COMPLETED));
    }

    @Test
    void findServicesByUserTest() {
        when(repository.findByUserOrderByDeliveryDate(userMocked)).thenReturn(Arrays.asList(orderMocked));
        List<OrderDTO> servicesByUser = service.findServicesByUser(userMocked);
        assertThat(servicesByUser.size()).isEqualTo(1);
    }

    @Test
    void findServicesByUserValidateContentTest() {
        when(orderMocked.getDescription()).thenReturn("Order test");
        when(repository.findByUserOrderByDeliveryDate(userMocked)).thenReturn(Arrays.asList(orderMocked));
        List<OrderDTO> servicesByUser = service.findServicesByUser(userMocked);
        assertThat(servicesByUser.get(0).getDescription()).isEqualTo("Order test");
    }

    @Test
    void findServicesByUserEmptyTest() {
        List<OrderDTO> servicesByUser = service.findServicesByUser(userMocked);
        assertThat(servicesByUser.isEmpty()).isTrue();
    }
}