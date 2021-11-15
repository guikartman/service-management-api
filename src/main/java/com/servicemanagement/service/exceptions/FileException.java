package com.servicemanagement.service.exceptions;

public class FileException extends RuntimeException {
    public FileException(String msg, Throwable cause) {
        super(msg, cause);
    }
}
