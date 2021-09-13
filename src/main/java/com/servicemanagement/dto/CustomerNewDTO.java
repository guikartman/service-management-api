package com.servicemanagement.dto;

import com.servicemanagement.domain.Cellphone;
import lombok.*;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

import java.io.Serializable;
import java.util.Set;

@AllArgsConstructor
@RequiredArgsConstructor
@ToString
@Getter
@Setter
public class CustomerNewDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    @Email
    private String email;

    @NotEmpty
    @Length(min = 5)
    private String name;

    private Set<Cellphone> cellphones;
}
