package com.servicemanagement.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.servicemanagement.domain.Customer;
import com.servicemanagement.domain.Order;
import com.servicemanagement.domain.User;
import com.servicemanagement.domain.enums.Status;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
public class OrderDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long id;

    @NotEmpty(message = "O serviço deve conter 1 título.")
    private String title;

    @NotNull(message = "O serviço deve conter 1 data de ínicio.")
    @JsonFormat(shape = JsonFormat.Shape.STRING,pattern = "dd/MM/yyyy")
    private LocalDate startDate;

    @NotNull(message = "O serviço deve conter 1 data de entrega.")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
    private LocalDate deliveryDate;

    @NotEmpty(message = "O serviço deve conter uma descrição.")
    private String description;

    @NotNull(message = "O serviço deve conter 1 preço.")
    private BigDecimal price;

    private Status status;

    private Boolean isPayed;

    private Customer customer;

    @JsonIgnore
    private User user;

    public OrderDTO(){
    }

    public OrderDTO(Order order) {
        this.id = order.getId();
        this.title = order.getTitle();
        this.startDate = order.getStartDate();
        this.deliveryDate = order.getDeliveryDate();
        this.description = order.getDescription();
        this.price = order.getPrice();
        this.status = order.getStatus();
        this.isPayed = order.getIsPayed();
        this.customer = order.getCustomer();
        this.user = order.getUser();
    }

    public OrderDTO(String title, LocalDate startDate, LocalDate deliveryDate, String description, BigDecimal price, Boolean isPayed, Customer customer) {
        this.title = title;
        this.startDate = startDate;
        this.deliveryDate = deliveryDate;
        this.description = description;
        this.price = price;
        this.status = Status.OPEN;
        this.isPayed = isPayed;
        this.customer = customer;
    }
}
