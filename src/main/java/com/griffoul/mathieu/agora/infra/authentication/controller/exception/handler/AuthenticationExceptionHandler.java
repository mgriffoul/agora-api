package com.griffoul.mathieu.agora.infra.authentication.controller.exception.handler;

import com.griffoul.mathieu.agora.infra.authentication.model.SignUpErrorMessage;
import com.griffoul.mathieu.agora.infra.authentication.model.SignUpResponse;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.stream.Collectors;

@ControllerAdvice
public class AuthenticationExceptionHandler extends ResponseEntityExceptionHandler {

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        SignUpResponse signUpResponse = new SignUpResponse(SignUpErrorMessage.FIELD_IN_ERROR.name());
        signUpResponse.setErroneousField(
                ex.getBindingResult().getAllErrors().stream()
                        .map(DefaultMessageSourceResolvable::getDefaultMessage)
                        .collect(Collectors.toList())
        );
        return new ResponseEntity<>(signUpResponse, HttpStatus.BAD_REQUEST);
    }

}
