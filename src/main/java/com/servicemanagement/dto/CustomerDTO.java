package com.servicemanagement.dto;

import com.servicemanagement.domain.Cellphone;
import com.servicemanagement.domain.Customer;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Getter
@Setter
public class CustomerDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long id;

    @Email
    private String email;

    @NotEmpty
    @Length(min = 5)
    private String name;

    @NotNull
    Cellphone cellphone;

    public CustomerDTO(){
    }

    public CustomerDTO(Customer customer) {
        this.id = customer.getId();
        this.email = customer.getEmail();
        this.name = customer.getName();
        this.cellphone = customer.getCellphone();
    }

    public CustomerDTO(Long id, String email, String name, Cellphone cellphone) {
        this.id = id;
        this.email = email;
        this.name = name;
        this.cellphone = cellphone;
    }
}
