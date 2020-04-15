package com.griffoul.mathieu.agora.infra.authentication.model;

import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

public class SignedUpUser implements Serializable {

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

    public SignedUpUser withMail(String mail) {
        this.mail = mail;
        return this;
    }

    public SignedUpUser withUsername(String username) {
        this.username = username;
        return this;
    }

}
