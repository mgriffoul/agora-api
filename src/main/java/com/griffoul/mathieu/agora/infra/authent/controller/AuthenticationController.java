package com.griffoul.mathieu.agora.infra.authent.controller;

import com.griffoul.mathieu.agora.infra.authent.exception.AuthenticationException;
import com.griffoul.mathieu.agora.infra.authent.model.AuthenticationRequest;
import com.griffoul.mathieu.agora.infra.authent.model.AuthenticationResponse;
import com.griffoul.mathieu.agora.infra.authent.service.AuthenticationTokenService;
import com.griffoul.mathieu.agora.infra.authent.service.AuthenticationUserDetailsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/test", produces = MediaType.APPLICATION_JSON_VALUE)
public class AuthenticationController {

    private Logger logger = LoggerFactory.getLogger(AuthenticationController.class);

    private AuthenticationManager authenticationManager;
    private AuthenticationTokenService authenticationTokenService;
    private AuthenticationUserDetailsService userDetailsService;

    public AuthenticationController(AuthenticationManager authenticationManager, AuthenticationTokenService authenticationTokenService, AuthenticationUserDetailsService userDetailsService) {
        this.authenticationManager = authenticationManager;
        this.authenticationTokenService = authenticationTokenService;
        this.userDetailsService = userDetailsService;
    }

    @PostMapping("/authenticate")
    public ResponseEntity<?> createAuthenticationToken(@RequestBody AuthenticationRequest authenticationRequest) {
        try {
            authenticate(authenticationRequest.getUsername(), authenticationRequest.getPassword());
        } catch (AuthenticationException e) {
            logger.error(e.getMessage(), e);
        }
        final UserDetails userDetails = userDetailsService
                .loadUserByUsername(authenticationRequest.getUsername());
        final String token = authenticationTokenService.generateToken(userDetails);
        return ResponseEntity.ok(new AuthenticationResponse(token));
    }

    private void authenticate(String username, String password) {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        } catch (DisabledException e) {
            throw new AuthenticationException("USER_DISABLED", e);
        } catch (BadCredentialsException e) {
            throw new AuthenticationException("ERROR JWT INVALID_CREDENTIALS", e);
        }
    }
}
