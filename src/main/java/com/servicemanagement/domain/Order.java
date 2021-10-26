package com.servicemanagement.domain;

import com.servicemanagement.domain.enums.Status;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@RequiredArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@Entity
@Table(name = "tb_order")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ORDER_ID")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "USER_ID")
    private final User user;

    @ManyToOne
    @JoinColumn(name = "CUSTOMER_ID")
    private Customer customer;

    private String title;
    private LocalDate startDate;
    private LocalDate deliveryDate;
    private String description;
    private BigDecimal price;
    private Status status;
    private Boolean isPayed;
    private String imageUrl;

    public Order() {
        user = null;
        status = Status.OPEN;
    }

    public Order(User user, Customer customer, String title, LocalDate startDate, LocalDate deliveryDate, String description, BigDecimal price, Boolean isPayed, String imageUrl) {
        this.user = user;
        this.customer = customer;
        this.title = title;
        this.startDate = startDate;
        this.deliveryDate = deliveryDate;
        this.description = description;
        this.price = price;
        this.isPayed = isPayed;
        this.status = Status.OPEN;
        this.imageUrl = imageUrl;
    }

    public Order(Long id,User user, Customer customer, String title, LocalDate startDate, LocalDate deliveryDate, String description, BigDecimal price, String imageUrl) {
        this.id = id;
        this.user = user;
        this.customer = customer;
        this.title = title;
        this.startDate = startDate;
        this.deliveryDate = deliveryDate;
        this.description = description;
        this.price = price;
        this.isPayed = false;
        this.status = Status.OPEN;
        this.imageUrl = imageUrl;
    }

    public Order(Long id, User user, Customer customer, String title, LocalDate startDate, LocalDate deliveryDate, String description, BigDecimal price, Status status, Boolean isPayed, String imageUrl) {
        this.id = id;
        this.user = user;
        this.customer = customer;
        this.title = title;
        this.startDate = startDate;
        this.deliveryDate = deliveryDate;
        this.description = description;
        this.price = price;
        this.status = status;
        this.isPayed = isPayed;
        this.imageUrl = imageUrl;
    }
}
