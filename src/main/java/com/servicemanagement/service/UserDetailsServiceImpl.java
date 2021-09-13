package com.servicemanagement.service;

import com.servicemanagement.repository.UserRepository;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Arrays;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private UserRepository userRepository;

    public UserDetailsServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        var user = userRepository.findByEmail(username).orElseThrow(() -> new UsernameNotFoundException("Usuário e/ou senha incorretos"));
        return new org.springframework.security.core.userdetails.User(username, user.getPassword(), Arrays.asList(new SimpleGrantedAuthority("USER")));
    }
}
