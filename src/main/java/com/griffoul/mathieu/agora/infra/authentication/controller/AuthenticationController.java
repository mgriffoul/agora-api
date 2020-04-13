package com.griffoul.mathieu.agora.infra.authentication.controller;

import com.griffoul.mathieu.agora.infra.authentication.exception.AuthenticationException;
import com.griffoul.mathieu.agora.infra.authentication.model.AuthenticationSignInRequest;
import com.griffoul.mathieu.agora.infra.authentication.model.AuthenticationSignInResponse;
import com.griffoul.mathieu.agora.infra.authentication.model.AuthenticationSignUpRequest;
import com.griffoul.mathieu.agora.infra.authentication.service.AuthenticationTokenService;
import com.griffoul.mathieu.agora.infra.authentication.service.AuthenticationUserDetailsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping(value = "/authentication", produces = MediaType.APPLICATION_JSON_VALUE)
public class AuthenticationController {

    private Logger logger = LoggerFactory.getLogger(AuthenticationController.class);

    private AuthenticationManager authenticationManager;
    private AuthenticationTokenService authenticationTokenService;
    private AuthenticationUserDetailsService userDetailsService;

    @Autowired
    public AuthenticationController(AuthenticationManager authenticationManager, AuthenticationTokenService authenticationTokenService, AuthenticationUserDetailsService userDetailsService) {
        this.authenticationManager = authenticationManager;
        this.authenticationTokenService = authenticationTokenService;
        this.userDetailsService = userDetailsService;
    }

    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationSignInResponse> createAuthenticationToken(@RequestBody AuthenticationSignInRequest authenticationSignInRequest) {
        try {
            authenticate(authenticationSignInRequest.getUsername(), authenticationSignInRequest.getPassword());
        } catch (AuthenticationException e) {
            logger.error(e.getMessage(), e);
        }
        final UserDetails userDetails = userDetailsService
                .loadUserByUsername(authenticationSignInRequest.getUsername());
        final String token = authenticationTokenService.generateToken(userDetails);
        return ResponseEntity.ok(new AuthenticationSignInResponse(token));
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

    @PostMapping("/signup")
    private void signUp(@Valid @RequestBody AuthenticationSignUpRequest authenticationSignUpRequest) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            authenticationSignUpRequest.getUsername(),
                            authenticationSignUpRequest.getPassword())
            );
        } catch (DisabledException e) {
            throw new AuthenticationException("USER_DISABLED", e);
        } catch (BadCredentialsException e) {
            throw new AuthenticationException("ERROR JWT INVALID_CREDENTIALS", e);
        }
    }
}
