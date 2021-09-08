package com.servicemanagement.domain;

import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@RequiredArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode
@Entity
@Table(name = "tb_customer")
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "CUSTOMER_ID")
    private Long id;

    private String email;
    private String name;

    @ManyToOne
    @JoinColumn(name = "USER_ID")
    private final User user;

    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL)
    private final List<Service> services = new ArrayList<>();

    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL)
    private final Set<Cellphone> cellphones = new HashSet<>();

    public Customer () {
        this.user = null;
    }

    public Customer(String email, String name, User user) {
        this.email = email;
        this.name = name;
        this.user = user;
    }
}
