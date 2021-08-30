package com.servicemanagement.service.exceptions;

public class UserAlreadyPresentException extends RuntimeException{

    public UserAlreadyPresentException(String email) {
        super(String.format("O email %s já existente no sistema.", email));
    }
}
