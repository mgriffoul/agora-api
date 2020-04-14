package com.griffoul.mathieu.agora.infra.authentication.model;

import java.io.Serializable;

public class SignedUpUser implements Serializable {

    private String username;
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
