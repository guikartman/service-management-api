package com.servicemanagement.service;

import com.servicemanagement.domain.User;
import com.servicemanagement.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Arrays;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.given;
import static org.mockito.MockitoAnnotations.openMocks;

class UserDetailsServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserDetailsServiceImpl userDetailsService;

    @BeforeEach
    public void setUp() {
       openMocks(this);
    }

    @Test
    void loadUserByUsernameEmailTest() {
        User user = new User("test", "test@gmail.com", "test123");
        given(userRepository.findByEmail("test@gmail.com")).willReturn(Optional.of(user));

        org.springframework.security.core.userdetails.User expectedUser =
                new org.springframework.security.core.userdetails.User(
                        user.getEmail(),
                        user.getPassword(),
                        Arrays.asList(new SimpleGrantedAuthority("USER")));

        assertThat(userDetailsService.loadUserByUsername("test@gmail.com")).isEqualTo(expectedUser);
    }

    @Test
    void loadUserByUsernameExceptionTest() {
        assertThrows(UsernameNotFoundException.class, () -> {
            userDetailsService.loadUserByUsername("test");
        });
    }
}