package com.servicemanagement.controller;

import com.servicemanagement.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.ZoneId;
import java.time.ZonedDateTime;

@RestController
@Slf4j
@Validated
public class UserController {

    private final UserRepository repository;

    private final PasswordEncoder passwordEncoder;

    @Autowired
    UserController(UserRepository repository, PasswordEncoder passwordEncoder) {
        this.repository = repository;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping(value = "/time", produces = "application/json")
    public ZonedDateTime currentTime() {
        return ZonedDateTime.now();
    }

    @GetMapping(value = "secretTime", produces = "application/json")
    public ZonedDateTime currentSecretTime() {
        return ZonedDateTime.now(ZoneId.of("UTC"));
    }

}
