package com.griffoul.mathieu.agora.infra.authentication.model;

import java.io.Serializable;

public class SignInResponse implements Serializable {

    private static final long serialVersionUID = -8091879091924046844L;

    private final String jwtToken;

    public SignInResponse(String jwtToken) {
        this.jwtToken = jwtToken;
    }

    public String getToken() {
        return this.jwtToken;
    }

}
