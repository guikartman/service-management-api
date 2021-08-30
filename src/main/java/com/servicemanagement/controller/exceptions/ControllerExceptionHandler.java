package com.servicemanagement.controller.exceptions;

import com.servicemanagement.service.exceptions.EmailNotFoundException;
import com.servicemanagement.service.exceptions.UserAlreadyPresentException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import java.time.Instant;

@ControllerAdvice
public class ControllerExceptionHandler {

    @ExceptionHandler(UserAlreadyPresentException.class)
    public ResponseEntity<StandardError> userAlreadyPresentException(UserAlreadyPresentException e, HttpServletRequest request){
        String message = "Usuário já cadastrado no sistema.";
        HttpStatus status = HttpStatus.BAD_REQUEST;
        StandardError err = new StandardError(Instant.now(), status.value(), message, e.getMessage(), request.getRequestURI());
        return ResponseEntity.status(status).body(err);
    }

    @ExceptionHandler(EmailNotFoundException.class)
    public ResponseEntity<StandardError> emailNotFoundException(EmailNotFoundException e, HttpServletRequest request){
        String message = "Email não está cadastrado.";
        HttpStatus status = HttpStatus.BAD_REQUEST;
        StandardError err = new StandardError(Instant.now(), status.value(), message, e.getMessage(), request.getRequestURI());
        return ResponseEntity.status(status).body(err);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<StandardError> defaultException(Exception e, HttpServletRequest  request) {
        HttpStatus status = HttpStatus.BAD_REQUEST;
        StandardError err = new StandardError(Instant.now(), status.value(), e.getMessage(),e.getMessage(), request.getRequestURI());
        return ResponseEntity.status(status).body(err);
    }
}