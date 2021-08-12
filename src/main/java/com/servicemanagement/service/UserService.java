package com.servicemanagement.service;

import com.servicemanagement.config.UserSS;
import com.servicemanagement.domain.User;
import com.servicemanagement.dto.UserDTO;
import com.servicemanagement.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class UserService {

    private UserRepository repo;
    private BCryptPasswordEncoder pe;

    @Autowired
    public UserService(UserRepository repo, BCryptPasswordEncoder pe) {
        this.repo = repo;
        this.pe = pe;
    }

    public static UserSS authenticated() {
        try {
            return (UserSS) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        }
        catch (Exception e) {
            return null;
        }
    }

    public UserDTO findByUsername(String username) {
        UserSS user = UserService.authenticated();
		if (user == null && !user.equals(user.getUsername())) {
			throw new RuntimeException("Acesso negado");
		}
        User authUser = repo.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("Usuario e/ou senha incorretos."));
        if (authUser == null) {
            authUser = repo.findByEmail(username).orElseThrow(() -> new UsernameNotFoundException("Usuario e/ou senha incorretos."));
        }
        return new UserDTO(authUser);
    }

}
