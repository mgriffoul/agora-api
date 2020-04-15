package com.griffoul.mathieu.agora.infra.authentication.model;

import java.io.Serializable;
import java.util.List;

public class SignUpResponse implements Serializable {

    private SignedUpUser signedUpUser;

    private String jwtToken;

    private String message;

    private List<String> erroneousField;

    public SignUpResponse(final SignedUpUser signedUpUser, final String jwtToken, final String message) {
        this.signedUpUser = signedUpUser;
        this.jwtToken = jwtToken;
        this.message = message;
    }

    public SignUpResponse(String message) {
        this.message = message;
    }

    public SignedUpUser getSignedUpUser() {
        return signedUpUser;
    }

    public void setSignedUpUser(SignedUpUser signedUpUser) {
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
