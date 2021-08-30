package com.servicemanagement.dto;

import com.servicemanagement.domain.User;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class UserDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    private String name;
    private String email;

    public UserDTO(User user) {
        name = user.getName();
        email = user.getEmail();
    }
}
