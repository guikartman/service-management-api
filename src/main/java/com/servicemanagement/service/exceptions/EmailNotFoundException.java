package com.servicemanagement.service.exceptions;

public class EmailNotFoundException extends RuntimeException{

    public EmailNotFoundException(String email) {
        super(String.format("O email %s não está cadastrado no sistema.", email));
    }
}
