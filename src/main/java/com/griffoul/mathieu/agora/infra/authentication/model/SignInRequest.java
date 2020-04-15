package com.griffoul.mathieu.agora.infra.authentication.model;

import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

public class SignInRequest implements Serializable {

    @ApiModelProperty(value = "Username of the user to sign in", required = true)
    private String username;

    @ApiModelProperty(value = "Password of the user to sign in", required = true)
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
