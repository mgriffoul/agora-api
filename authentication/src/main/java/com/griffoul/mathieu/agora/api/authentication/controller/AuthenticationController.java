package com.griffoul.mathieu.agora.api.authentication.controller;

import com.griffoul.mathieu.agora.api.authentication.exception.AuthenticationException;
import com.griffoul.mathieu.agora.api.authentication.exception.BddTechnicalErrorException;
import com.griffoul.mathieu.agora.api.authentication.model.AuthenticationUser;
import com.griffoul.mathieu.agora.api.authentication.model.SignInRequest;
import com.griffoul.mathieu.agora.api.authentication.model.SignInResponse;
import com.griffoul.mathieu.agora.api.authentication.model.SignUpErrorMessage;
import com.griffoul.mathieu.agora.api.authentication.model.SignUpRequest;
import com.griffoul.mathieu.agora.api.authentication.model.SignUpResponse;
import com.griffoul.mathieu.agora.api.authentication.service.AgoraUserService;
import com.griffoul.mathieu.agora.api.authentication.service.AuthenticationTokenService;
import com.griffoul.mathieu.agora.api.authentication.service.AuthenticationUserDetailsService;
import com.griffoul.mathieu.agora.api.data.data.model.AgoraUser;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.persistence.NoResultException;
import javax.validation.Valid;

@RestController
@RequestMapping(value = "/authentication", produces = MediaType.APPLICATION_JSON_VALUE)
@Api("/authentication")
public class AuthenticationController {

    private Logger logger = LoggerFactory.getLogger(AuthenticationController.class);

    private AuthenticationTokenService authenticationTokenService;
    private AuthenticationUserDetailsService userDetailsService;
    private AgoraUserService agoraUserService;

    @Autowired
    public AuthenticationController(AuthenticationTokenService authenticationTokenService,
                                    AuthenticationUserDetailsService userDetailsService,
                                    AgoraUserService agoraUserService) {
        this.authenticationTokenService = authenticationTokenService;
        this.userDetailsService = userDetailsService;
        this.agoraUserService = agoraUserService;
    }

    @PostMapping("/signin")
    @ApiOperation(value = "Sign in a user", response = SignInResponse.class)
    @ApiResponses({
            @ApiResponse(code = 200, message = "Return jwtToken if credentials matching", response = SignInResponse.class)
    })
    public ResponseEntity<SignInResponse> signIn(
            @RequestBody SignInRequest signInRequest) {
        try {
            agoraUserService.authenticate(signInRequest.getMail(), signInRequest.getPassword());
        } catch (NoResultException e) {
            logger.error(e.getMessage(), e);
            SignInResponse signInResponse = new SignInResponse(null, SignUpErrorMessage.BAD_CREDENTIALS.name());
            return new ResponseEntity<>(signInResponse, HttpStatus.BAD_REQUEST);
        } catch (AuthenticationException e) {
            logger.error(e.getMessage(), e);
            SignInResponse signInResponse = new SignInResponse(null, "TECHNICAL ERROR");
            return new ResponseEntity<>(signInResponse, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        final UserDetails userDetails = userDetailsService.loadUserByUsername(signInRequest.getMail());
        final String token = authenticationTokenService.generateToken(userDetails);

        AuthenticationUser user = mapToAuthenticationUser(agoraUserService.getUserByMail(signInRequest.getMail()));

        return ResponseEntity.ok(new SignInResponse(token).withAuthenticationUser(user).withMessage("SUCCESS"));
    }

    private AuthenticationUser mapToAuthenticationUser(AgoraUser agoraUser) {
        return new AuthenticationUser().withMail(agoraUser.getMail()).withUsername(agoraUser.getUsername());
    }

    @PostMapping("/signup")
    @ApiOperation(value = "Sign up a user", response = SignUpResponse.class)
    @ApiResponses({
            @ApiResponse(code = 200, message = "SUCCESS", response = SignUpResponse.class),
            @ApiResponse(code = 400, message = "MAIL_UNAVAILABLE", response = SignUpResponse.class),
            @ApiResponse(code = 400, message = "USERNAME_UNAVAILABLE", response = SignUpResponse.class),
            @ApiResponse(code = 400, message = "FIELD_IN_ERROR", response = SignUpResponse.class),
            @ApiResponse(code = 500, message = "ERROR : database temporarily unavailable", response = SignUpResponse.class)
    })
    public ResponseEntity<SignUpResponse> signUp(
            @ApiParam(required = true, name = "signUpRequest") @Valid @RequestBody SignUpRequest signUpRequest) {
        AuthenticationUser signedUpUser = null;
        try {
            signedUpUser = agoraUserService.createUser(signUpRequest);
        } catch (AuthenticationException e) {
            SignUpResponse signUpResponse = new SignUpResponse(null, null, e.getMessage());
            return new ResponseEntity<>(signUpResponse, HttpStatus.BAD_REQUEST);
        } catch (BddTechnicalErrorException e) {
            SignUpResponse signUpResponse = new SignUpResponse(null, null, e.getMessage());
            return new ResponseEntity<>(signUpResponse, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        final UserDetails userDetails = userDetailsService.loadUserByUsername(signedUpUser.getMail());
        final String token = authenticationTokenService.generateToken(userDetails);
        return new ResponseEntity<>(new SignUpResponse(signedUpUser, token, "SUCCESS") {
        }, HttpStatus.OK);
    }


}
