package com.griffoul.mathieu.agora.api.authentication.model;

import io.swagger.annotations.ApiModelProperty;

public class AuthenticationUser {

    @ApiModelProperty("Username of the created user")
    private String username;

    @ApiModelProperty("Mail of the created user")
    private String mail;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public AuthenticationUser withMail(String mail) {
        this.mail = mail;
        return this;
    }

    public AuthenticationUser withUsername(String username) {
        this.username = username;
        return this;
    }
}
