package com.servicemanagement.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;

@RequiredArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "tb_cellphone")
public class Cellphone {

    @JsonIgnore
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "CELLPHONE_ID")
    private Long id;

    @Column(name = "DDD", length = 2, nullable = false)
    private Integer ddd;

    @Column(name = "number", length = 9, nullable = false)
    private Integer cellphoneNumber;

    private Boolean isWhatsapp;

    @JsonIgnore
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "CUSTOMER_ID")
    private Customer customer;

    public Cellphone(Integer ddd,Integer cellphoneNumber, Boolean isWhatsapp, Customer customer) {
        this.ddd = ddd;
        this.cellphoneNumber = cellphoneNumber;
        this.isWhatsapp = isWhatsapp;
        this.customer = customer;
    }

    public Cellphone(Integer ddd,Integer cellphoneNumber, Boolean isWhatsapp) {
        this.ddd = ddd;
        this.cellphoneNumber = cellphoneNumber;
        this.isWhatsapp = isWhatsapp;
    }
}
