package com.griffoul.mathieu.agora.api.authentication.model;

import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

public class SignInRequest implements Serializable {

    @ApiModelProperty(value = "Mail of the user to sign in", required = true)
    private String mail;

    @ApiModelProperty(value = "Password of the user to sign in", required = true)
    private String password;

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
