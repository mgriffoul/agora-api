package com.griffoul.mathieu.agora.infra.authentication.controller;

import com.griffoul.mathieu.agora.infra.authentication.exception.AuthenticationException;
import com.griffoul.mathieu.agora.infra.authentication.model.SignInRequest;
import com.griffoul.mathieu.agora.infra.authentication.model.SignInResponse;
import com.griffoul.mathieu.agora.infra.authentication.model.SignUpRequest;
import com.griffoul.mathieu.agora.infra.authentication.model.SignUpResponse;
import com.griffoul.mathieu.agora.infra.authentication.model.SignedUpUser;
import com.griffoul.mathieu.agora.infra.authentication.service.AgoraUserService;
import com.griffoul.mathieu.agora.infra.authentication.service.AuthenticationTokenService;
import com.griffoul.mathieu.agora.infra.authentication.service.AuthenticationUserDetailsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

import static java.util.Collections.singletonList;

@RestController
@RequestMapping(value = "/authentication", produces = MediaType.APPLICATION_JSON_VALUE)
public class AuthenticationController {

    private Logger logger = LoggerFactory.getLogger(AuthenticationController.class);

    private AuthenticationManager authenticationManager;
    private AuthenticationTokenService authenticationTokenService;
    private AuthenticationUserDetailsService userDetailsService;
    private AgoraUserService agoraUserService;

    @Autowired
    public AuthenticationController(AuthenticationManager authenticationManager,
                                    AuthenticationTokenService authenticationTokenService,
                                    AuthenticationUserDetailsService userDetailsService,
                                    AgoraUserService agoraUserService) {
        this.authenticationManager = authenticationManager;
        this.authenticationTokenService = authenticationTokenService;
        this.userDetailsService = userDetailsService;
        this.agoraUserService = agoraUserService;
    }

    @PostMapping("/signin")
    public ResponseEntity<SignInResponse> signIn(@RequestBody SignInRequest signInRequest) {
        try {
            authenticate(signInRequest.getUsername(), signInRequest.getPassword());
        } catch (AuthenticationException e) {
            logger.error(e.getMessage(), e);
        }
        final UserDetails userDetails = userDetailsService.loadUserByUsername(signInRequest.getUsername());
        final String token = authenticationTokenService.generateToken(userDetails);
        return ResponseEntity.ok(new SignInResponse(token));
    }

    private void authenticate(String username, String password) throws AuthenticationException {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        } catch (DisabledException e) {
            throw new AuthenticationException("USER_DISABLED", e);
        } catch (BadCredentialsException e) {
            throw new AuthenticationException("ERROR JWT INVALID_CREDENTIALS", e);
        }
    }

    @PostMapping("/signup")
    public ResponseEntity<SignUpResponse> signUp(@Valid @RequestBody SignUpRequest signUpRequest) {
        SignedUpUser signedUpUser = null;
        try {
            signedUpUser = agoraUserService.createUser(signUpRequest);
        } catch (AuthenticationException e) {
            SignUpResponse signUpResponse = new SignUpResponse(SignUpResponse.SignUpMessage.FIELD_IN_ERROR,
                    singletonList(e.getMessage()));
            return new ResponseEntity<>(signUpResponse, HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(new SignUpResponse(signedUpUser, SignUpResponse.SignUpMessage.SUCCESS) {
            }, HttpStatus.OK);
    }

}
