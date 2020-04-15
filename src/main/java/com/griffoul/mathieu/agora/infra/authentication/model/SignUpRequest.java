package com.griffoul.mathieu.agora.infra.authentication.model;

import io.swagger.annotations.ApiParam;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import java.io.Serializable;

public class SignUpRequest implements Serializable {

    @NotEmpty(message = "Username must be provided")
    @ApiParam(value = "username", required = true)
    private String username;

    @NotEmpty(message = "Password must be provided")
    @ApiParam(value = "password", required = true)
    private String password;

    @Email(message = "Provided mail is not conform")
    @NotEmpty(message = "Username must be provided")
    @ApiParam(value = "mail", required = true)
    private String mail;

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

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }
}
