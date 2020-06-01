package com.griffoul.mathieu.agora.api.infra.authentication.model;

import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

public class SignInResponse implements Serializable {

    private static final long serialVersionUID = -8091879091924046844L;

    @ApiModelProperty("The user")
    private AuthenticationUser signedInUser;

    @ApiModelProperty("The jwt authentication token returned by api when success")
    private String jwtToken;

    @ApiModelProperty("Extra message")
    private String message;

    public SignInResponse(String jwtToken) {
        this.jwtToken = jwtToken;
    }

    public SignInResponse(String jwtToken, String message) {
        this.jwtToken = jwtToken;
        this.message = message;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public AuthenticationUser getSignedInUser() {
        return signedInUser;
    }

    public void setSignedInUser(AuthenticationUser signedInUser) {
        this.signedInUser = signedInUser;
    }

    public String getJwtToken() {
        return jwtToken;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public SignInResponse withJwtToken(String jwtToken){
        this.jwtToken = jwtToken;
        return this;
    }

    public SignInResponse withAuthenticationUser(AuthenticationUser authenticationUser){
        this.signedInUser = authenticationUser;
        return this;
    }

    public SignInResponse withMessage(String  message){
        this.message = message;
        return this;
    }
}
