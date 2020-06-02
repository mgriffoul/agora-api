package com.griffoul.mathieu.agora.api.authentication.model;

import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.List;

public class SignUpResponse implements Serializable {

    @ApiModelProperty("The created user summary")
    private AuthenticationUser signedUpUser;

    @ApiModelProperty("The jwt authentication token returned by api when success")
    private String jwtToken;

    @ApiModelProperty("Extra error message in case of failure")
    private String message;

    @ApiModelProperty("List of fields in error in case of failure")
    private List<String> erroneousField;

    public SignUpResponse(final AuthenticationUser signedUpUser, final String jwtToken, final String message) {
        this.signedUpUser = signedUpUser;
        this.jwtToken = jwtToken;
        this.message = message;
    }

    public SignUpResponse(String message) {
        this.message = message;
    }

    public AuthenticationUser getSignedUpUser() {
        return signedUpUser;
    }

    public void setSignedUpUser(AuthenticationUser signedUpUser) {
        this.signedUpUser = signedUpUser;
    }

    public String getJwtToken() {
        return jwtToken;
    }

    public void setJwtToken(String jwtToken) {
        this.jwtToken = jwtToken;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<String> getErroneousField() {
        return erroneousField;
    }

    public void setErroneousField(List<String> erroneousField) {
        this.erroneousField = erroneousField;
    }
}
