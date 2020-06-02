package com.griffoul.mathieu.agora.api.authentication.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "authentication")
public class AuthenticationProperties {

    private String passwordSeed;

    public String getPasswordSeed() {
        return passwordSeed;
    }

    public void setPasswordSeed(String passwordSeed) {
        this.passwordSeed = passwordSeed;
    }
}
