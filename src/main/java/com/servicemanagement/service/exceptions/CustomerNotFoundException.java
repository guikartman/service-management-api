package com.servicemanagement.service.exceptions;

public class CustomerNotFoundException extends RuntimeException{

    public CustomerNotFoundException(Long id) {
        super("Cliente não encontrado, com o id: "+id);
    }
}
