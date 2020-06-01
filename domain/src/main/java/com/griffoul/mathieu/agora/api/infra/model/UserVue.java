package com.griffoul.mathieu.agora.api.infra.model;

import java.io.Serializable;

public class UserVue implements Serializable {

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
}
