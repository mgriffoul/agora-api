package com.griffoul.mathieu.agora.api.infra.authentication.controller;

import org.springframework.http.HttpEntity;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;


@RestController
@RequestMapping(value = "/check", produces = MediaType.APPLICATION_JSON_VALUE)
public class HealthCheckController {
    @GetMapping
    public HttpEntity<String> testController() {
        return ResponseEntity.of(Optional.of("{\"message\": \"Bonjour\"}"));
    }
}
