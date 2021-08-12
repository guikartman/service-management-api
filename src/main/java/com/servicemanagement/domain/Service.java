package com.servicemanagement.domain;

import com.servicemanagement.domain.enums.Status;
import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@RequiredArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode
@Entity
@Table(name = "SERVICE")
public class Service {

    @Id
    @GeneratedValue
    @Column(name = "SERVICE_ID")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "USER_ID")
    private final User user;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "CUSTOMER_ID")
    private final Customer customer;

    private String title;
    private LocalDate startDate;
    private LocalDate deliveryDate;
    private String description;
    private BigDecimal price;
    private Status status;
    private Boolean isPayed;

    public Service() {
        user = null;
        customer = null;
        status = Status.OPEN;
    }
}
