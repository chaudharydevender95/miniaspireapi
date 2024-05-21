package com.example.miniaspireapi.exception;

/**
 * @author devenderchaudhary
 */
public class ResourceNotFoundException extends RuntimeException{

    public ResourceNotFoundException(String message) {
        super(message);
    }
}
