package com.griffoul.mathieu.agora.infra.authentication.model;

import io.swagger.annotations.ApiParam;

import java.io.Serializable;

public class SignInRequest implements Serializable {

    @ApiParam(value = "username", required = true)
    private String username;

    @ApiParam(value = "password", required = true)
    private String password;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
