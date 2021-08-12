package com.servicemanagement.service;

import com.servicemanagement.domain.User;
import com.servicemanagement.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class DBService {

    @Autowired
    private BCryptPasswordEncoder pe;

    @Autowired
    private UserRepository userRepository;

    public void instantiateTestDatabase() {

        User usu1 = new User("admin", "admin@gmail.com", pe.encode("admin"));

        userRepository.save(usu1);
    }
}
