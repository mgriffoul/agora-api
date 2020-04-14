package com.griffoul.mathieu.agora.infra.authentication.model;

import java.io.Serializable;
import java.util.List;

public class SignUpResponse implements Serializable {

    private SignedUpUser signedUpUser;

    private SignUpMessage message;

    private List<String> erroneousField;

    public enum SignUpMessage implements Serializable {
        SUCCESS, MISSING_FIELD, FIELD_IN_ERROR, MAIL_UNAVAILABLE, USERNAME_UNAVAILABLE,
    }

    public SignUpResponse(SignUpMessage message) {
        this.message = message;
    }

    public SignUpResponse(SignedUpUser signedUpUser, SignUpMessage message) {
        this.signedUpUser = signedUpUser;
        this.message = message;
    }

    public SignUpResponse(SignUpMessage message, List<String> erroneousField) {
        this.message = message;
        this.erroneousField = erroneousField;
    }

    public SignedUpUser getSignedUpUser() {
        return signedUpUser;
    }

    public void setSignedUpUser(SignedUpUser signedUpUser) {
        this.signedUpUser = signedUpUser;
    }

    public SignUpMessage getMessage() {
        return message;
    }

    public void setMessage(SignUpMessage message) {
        this.message = message;
    }

    public List<String> getErroneousField() {
        return erroneousField;
    }

    public void setErroneousField(List<String> erroneousField) {
        this.erroneousField = erroneousField;
    }
}
