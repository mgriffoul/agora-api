package com.griffoul.mathieu.agora.infra.authentication.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

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
