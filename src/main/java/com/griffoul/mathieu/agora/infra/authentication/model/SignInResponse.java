package com.griffoul.mathieu.agora.infra.authentication.model;

import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

public class SignInResponse implements Serializable {

    private static final long serialVersionUID = -8091879091924046844L;

    @ApiModelProperty("The jwt authentication token returned by api when success")
    private final String jwtToken;

    @ApiModelProperty("Extra message")
    private String message;

    public SignInResponse(String jwtToken) {
        this.jwtToken = jwtToken;
    }

    public SignInResponse(String jwtToken, String message) {
        this.jwtToken = jwtToken;
        this.message = message;
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

}
