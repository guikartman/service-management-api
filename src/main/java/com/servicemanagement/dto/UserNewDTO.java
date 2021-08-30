package com.servicemanagement.dto;

import lombok.*;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

import java.io.Serializable;

@AllArgsConstructor
@RequiredArgsConstructor
@ToString
@Getter
@Setter
public class UserNewDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    @Length(min = 5)
    @NotEmpty
    private String name;

    @Email
    private String email;

    @Length(min = 8)
    @NotEmpty
    private String password;
}
