package com.servicemanagement.service.exceptions;

public class WrongPasswordException extends RuntimeException{

    public WrongPasswordException() {
        super("Senha informada não bate com a senha cadastrada.");
    }
}
