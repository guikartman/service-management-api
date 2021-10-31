package com.servicemanagement.service.exceptions;

public class OrderNotFoundException extends RuntimeException {

    public OrderNotFoundException(Long id) {
        super("Não foi possivel encontrar o serviço com o id: "+id);
    }
}
