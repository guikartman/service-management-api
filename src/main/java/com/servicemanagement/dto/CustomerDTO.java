package com.servicemanagement.dto;

import com.servicemanagement.domain.Cellphone;
import com.servicemanagement.domain.Customer;
import lombok.Getter;
import lombok.Setter;
import org.codehaus.jackson.annotate.JsonIgnore;

import java.io.Serializable;
import java.util.Set;

@Getter
@Setter
public class CustomerDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long id;
    private String email;
    private String name;

    private Set<Cellphone> cellphones;

    public CustomerDTO(){
    }

    public CustomerDTO(Customer customer) {
        this.id = customer.getId();
        this.email = customer.getEmail();
        this.name = customer.getName();
        this.cellphones = customer.getCellphones();
    }
}
