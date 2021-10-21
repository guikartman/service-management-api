package com.servicemanagement.service.exceptions;

public class OrderNotFoundException extends RuntimeException {

    public OrderNotFoundException(Long id) {
        super("Can't find the order with the id: "+id);
    }
}
