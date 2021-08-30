package com.servicemanagement.domain;

import lombok.*;

import javax.persistence.*;

@RequiredArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode
@Entity
@Table(name = "tb_cellphone")
public class Cellphone {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "CELLPHONE_ID")
    private Long id;

    private String cellphoneNumber;
    private Boolean isWhatsapp;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "CUSTOMER_ID")
    private final Customer customer;

    public Cellphone() {
        this.customer = null;
    }

    public Cellphone(String cellphoneNumber, Boolean isWhatsapp, Customer customer) {
        this.cellphoneNumber = cellphoneNumber;
        this.isWhatsapp = isWhatsapp;
        this.customer = customer;
    }
}
