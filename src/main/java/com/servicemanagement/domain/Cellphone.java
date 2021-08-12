package com.servicemanagement.domain;

import lombok.*;

import javax.persistence.*;

@RequiredArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode
@Entity
@Table(name = "CELLPHONE")
public class Cellphone {

    @Id
    @GeneratedValue
    @Column(name = "CELLPHONE_ID")
    private Long id;

    private String cellphoneNumber;
    private Boolean isWhatsapp;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "CUSTOMER_ID")
    private Customer customer;

}
