package com.servicemanagement.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@NoArgsConstructor
@Getter
@Setter
public class LoginDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    private String username;
    private String password;
}
