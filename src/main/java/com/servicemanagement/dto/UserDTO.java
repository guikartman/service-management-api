package com.servicemanagement.dto;

import com.servicemanagement.domain.User;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class UserDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long id;
    private String username;
    private String email;

    public UserDTO(User user) {
        id = user.getId();
        username = user.getUsername();
        email = user.getEmail();
    }
}
