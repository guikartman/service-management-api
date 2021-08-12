package com.servicemanagement.domain;

import lombok.*;

import javax.persistence.*;

@RequiredArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode
@Entity
@Table(name = "CUSTOMER")
public class Customer {

    @Id
    @GeneratedValue
    @Column(name = "CUSTOMER_ID")
    private Long id;

    private String email;
    private String name;
}
