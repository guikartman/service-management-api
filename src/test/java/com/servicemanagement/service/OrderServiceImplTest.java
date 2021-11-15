package com.servicemanagement.service;

import com.servicemanagement.domain.Customer;
import com.servicemanagement.domain.Order;
import com.servicemanagement.domain.User;
import com.servicemanagement.domain.enums.Status;
import com.servicemanagement.dto.OrderDTO;
import com.servicemanagement.dto.ReportDTO;
import com.servicemanagement.repository.OrderRepository;
import com.servicemanagement.service.exceptions.OrderNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.*;

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

    @Mock
    private Order openOrder;

    @Mock
    private Order inProgressOrder;

    @Mock
    private Order delayedOrder;

    @Mock
    private Order completedOrder;

    @Captor
    private ArgumentCaptor<Order> orderCaptor;

    private OrderServiceImpl service;

    @BeforeEach
    public void initTest() {
        openMocks(this);
        this.service = spy(new OrderServiceImpl(repository, s3ServiceMock));
        when(openOrder.getStatus()).thenReturn(Status.OPEN);
        when(openOrder.getPrice()).thenReturn(BigDecimal.valueOf(50));
        when(openOrder.getIsPayed()).thenReturn(false);
        when(openOrder.getStartDate()).thenReturn(LocalDate.now().plusDays(1));
        when(openOrder.getDeliveryDate()).thenReturn(LocalDate.now().plusDays(2));
        when(inProgressOrder.getStatus()).thenReturn(Status.OPEN);
        when(inProgressOrder.getPrice()).thenReturn(BigDecimal.TEN);
        when(inProgressOrder.getIsPayed()).thenReturn(true);
        when(inProgressOrder.getStartDate()).thenReturn(LocalDate.now());
        when(inProgressOrder.getDeliveryDate()).thenReturn(LocalDate.now().plusDays(2));
        when(delayedOrder.getStatus()).thenReturn(Status.OPEN);
        when(delayedOrder.getPrice()).thenReturn(BigDecimal.valueOf(50));
        when(delayedOrder.getIsPayed()).thenReturn(false);
        when(delayedOrder.getStartDate()).thenReturn(LocalDate.now().minusDays(2));
        when(delayedOrder.getDeliveryDate()).thenReturn(LocalDate.now().minusDays(1));
        when(completedOrder.getStatus()).thenReturn(Status.COMPLETED);
        when(completedOrder.getPrice()).thenReturn(BigDecimal.TEN);
        when(completedOrder.getIsPayed()).thenReturn(true);
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
    void updatePayedStatusFalseTest() {
        var order = new Order();
        order.setIsPayed(true);
        when(repository.findById(anyLong())).thenReturn(Optional.of(order));
        service.updatePayedStatus(1L);
        verify(repository, times(1)).save(orderCaptor.capture());
        assertThat(orderCaptor.getValue().getIsPayed()).isFalse();
    }

    @Test
    void updatePayedStatusTrueTest() {
        var order = new Order();
        order.setIsPayed(false);
        when(repository.findById(anyLong())).thenReturn(Optional.of(order));
        service.updatePayedStatus(1L);
        verify(repository, times(1)).save(orderCaptor.capture());
        assertThat(orderCaptor.getValue().getIsPayed()).isTrue();
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
    void buildCompleteReportTest() {
        when(openOrder.getStatus()).thenReturn(Status.OPEN);
        List<Order> orders = new ArrayList<>(Arrays.asList(openOrder, inProgressOrder, delayedOrder, completedOrder));
        when(repository.findByUserOrderByDeliveryDate(userMocked)).thenReturn(orders);
        ReportDTO reportDTO = service.retrieveReports(userMocked);
        assertThat(reportDTO.getTotalCash()).isEqualTo(BigDecimal.valueOf(120.0));
        assertThat(reportDTO.getTotalCashEarned()).isEqualTo(BigDecimal.valueOf(20.0));
        assertThat(reportDTO.getTotalOrder()).isEqualTo(4);
        assertThat(reportDTO.getTotalOrderOpen()).isEqualTo(1);
        assertThat(reportDTO.getTotalOrderInProgress()).isEqualTo(1);
        assertThat(reportDTO.getTotalOrderDelayed()).isEqualTo(1);
        assertThat(reportDTO.getTotalOrderCompleted()).isEqualTo(1);
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