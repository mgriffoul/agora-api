package com.griffoul.mathieu.agora.infra.authentication.exception;

public class AuthenticationException extends RuntimeException{
    public AuthenticationException(String message, Throwable cause) {
        super(message, cause);
    }
}
