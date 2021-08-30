package com.servicemanagement.service;

import com.servicemanagement.domain.User;
import com.servicemanagement.dto.UserNewDTO;
import com.servicemanagement.repository.UserRepository;
import com.servicemanagement.service.exceptions.EmailNotFoundException;
import com.servicemanagement.service.exceptions.UserAlreadyPresentException;
import com.servicemanagement.service.exceptions.WrongPasswordException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

class UserServiceImplTest {

    private UserServiceImpl userService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private EmailService emailService;

    private BCryptPasswordEncoder passwordEncoder;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        this.userService = new UserServiceImpl(userRepository, emailService);
        this.passwordEncoder = new BCryptPasswordEncoder();
    }

    @Test
    void createNewUserTest(){
        UserNewDTO userNewDTO = new UserNewDTO("User Test", "test@gmail.com", "test");
        given(userRepository.findByEmail("test@gmail.com")).willReturn(Optional.empty());
        given(userRepository.save(any(User.class))).willReturn(new User());
        userService.createNewUser(userNewDTO);
        verify(userRepository, times(1)).save(any(User.class));
        verify(emailService, times(1)).sendEmail(any(User.class),anyString(), anyString());
    }

    @Test
    void createNewUserEmailAlreadyExistsTest() {
        User user = new User("User Test", "test@gmail.com","test");
        UserNewDTO userNewDTO = new UserNewDTO("User Test", "test@gmail.com", "test");
        given(userRepository.findByEmail("test@gmail.com")).willReturn(Optional.of(user));
        assertThrows(UserAlreadyPresentException.class, () -> userService.createNewUser(userNewDTO));
        verify(userRepository, never()).save(any(User.class));
        verify(emailService, never()).sendEmail(any(User.class), anyString(), anyString());
    }

    @Test
    void retrievePasswordTest() {
        User user = new User("User Test", "test@gmail.com","test");
        given(userRepository.findByEmail("test@gmail.com")).willReturn(Optional.of(user));
        userService.retrievePassword("test@gmail.com");
        verify(userRepository, times(1)).save(any(User.class));
        verify(emailService, times(1)).sendEmail(any(User.class), anyString(), anyString());
    }

    @Test
    void retrievePasswordExceptionTest() {
        given(userRepository.findByEmail("test@gmail.com")).willReturn(Optional.empty());
        assertThrows(EmailNotFoundException.class,() -> userService.retrievePassword("test@gmail.com"));
        verify(userRepository, never()).save(any(User.class));
        verify(emailService, never()).sendEmail(any(User.class), anyString(), anyString());
    }

    @Test
    void changePasswordTest() {
        User user = new User("User Test", "test@gmail.com", passwordEncoder.encode("oldPassword"));
        given(userRepository.findByEmail("test@gmail.com")).willReturn(Optional.of(user));
        userService.changePassword("test@gmail.com","oldPassword","testPassword");
        verify(userRepository, times(1)).save(any(User.class));
        verify(emailService, times(1)).sendEmail(any(User.class), anyString(), anyString());
    }

    @Test
    void changePasswordExceptionTest() {
        given(userRepository.findByEmail("test@gmail.com")).willReturn(Optional.empty());
        assertThrows(EmailNotFoundException.class,() -> userService.changePassword("test@gmail.com","oldPassword", "testPassword"));
        verify(userRepository, never()).save(any(User.class));
        verify(emailService, never()).sendEmail(any(User.class), anyString(), anyString());
    }

    @Test
    void changeWrongPasswordExceptionTest() {
        User user = new User("User Test", "test@gmail.com","oldPassword");
        given(userRepository.findByEmail("test@gmail.com")).willReturn(Optional.of(user));
        assertThrows(WrongPasswordException.class,() -> userService.changePassword("test@gmail.com","password", "testPassword"));
        verify(userRepository, never()).save(any(User.class));
        verify(emailService, never()).sendEmail(any(User.class), anyString(), anyString());
    }

    @Test
    void findUserByEmailTest() {
        User expectedUser = new User("User Test", "test@gmail.com","test");
        given(userRepository.findByEmail("test@gmail.com")).willReturn(Optional.of(expectedUser));
        User actualUser = userService.findUserByEmail("test@gmail.com");
        assertThat(actualUser).isEqualTo(expectedUser);
    }

    @Test
    void findUserByEmailExceptionTest() {
        given(userRepository.findByEmail("test@gmail.com")).willReturn(Optional.empty());
        assertThrows(EmailNotFoundException.class, () -> userService.findUserByEmail("test@gmail.com"));
    }
}