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

    @Email(message = "O email deve ser valido.")
    private String email;

    @NotEmpty(message = "O campo nome deve ser preenchido.")
    @Length(min = 5, message = "O nome deve conter mais do que 5 caracteres.")
    private String name;

    @NotNull(message = "O cliente deve possuir 1 celular.")
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
