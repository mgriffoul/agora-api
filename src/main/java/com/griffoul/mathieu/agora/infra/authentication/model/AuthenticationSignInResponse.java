package com.griffoul.mathieu.agora.infra.authentication.model;

import java.io.Serializable;

public class AuthenticationSignInResponse implements Serializable {

    private static final long serialVersionUID = -8091879091924046844L;

    private final String jwttoken;

    public AuthenticationSignInResponse(String jwttoken) {
        this.jwttoken = jwttoken;
    }

    public String getToken() {
        return this.jwttoken;
    }

}
