package com.servicemanagement.domain;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Getter
@Setter
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

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "CELLPHONE_ID", referencedColumnName = "CELLPHONE_ID")
    private final Cellphone cellphone;

    public Customer () {
        this.cellphone = null;
        this.user = null;
    }

    public Customer(String email, String name, User user, Cellphone cellphone) {
        this.email = email;
        this.name = name;
        this.user = user;
        this.cellphone = cellphone;
    }

    public Customer(Long id,String email, String name, User user, Cellphone cellphone) {
        this.email = email;
        this.name = name;
        this.user = user;
        this.cellphone = cellphone;
    }
}
