package com.griffoul.mathieu.agora.api.infra.authentication.exception;

public class BddTechnicalErrorException extends Exception {
    public BddTechnicalErrorException(String message) {
        super(message);
    }
}
