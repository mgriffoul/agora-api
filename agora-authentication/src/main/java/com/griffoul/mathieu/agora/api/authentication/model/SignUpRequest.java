package com.griffoul.mathieu.agora.api.authentication.model;

import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import java.io.Serializable;

public class SignUpRequest implements Serializable {

    @NotEmpty(message = "Username must be provided")
    @ApiModelProperty(value = "Username of the user to create", required = true)
    private String username;

    @NotEmpty(message = "Password must be provided")
    @ApiModelProperty(value = "Password of the user to create", required = true)
    private String password;

    @Email(message = "Provided mail is not conform")
    @NotEmpty(message = "Username must be provided")
    @ApiModelProperty(value = "Mail of the user to create", required = true)
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
