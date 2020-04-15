package com.griffoul.mathieu.agora.infra.authentication.service;

import com.griffoul.mathieu.agora.infra.authentication.properties.JwtProperties;
import io.jsonwebtoken.MalformedJwtException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;

import static org.assertj.core.api.Assertions.assertThat;

class AuthenticationTokenServiceTest {

    private AuthenticationTokenService authenticationTokenService = new AuthenticationTokenService(new JwtPropertiesFake());

    @Test
    void generateToken_should_return_valid_token() {
        //Arrange
        UserDetails userDetails = new User("username", "password",
                new ArrayList<>());

        //Act
        String token = authenticationTokenService.generateToken(userDetails);
        boolean valid = authenticationTokenService.validateToken(token, userDetails);

        //Assert
        assertThat(token.length()).isEqualTo(178);
        assertThat(token).matches("^[A-Za-z0-9-_=]+\\.[A-Za-z0-9-_=]+\\.?[A-Za-z0-9-_.+/=]*$");
        assertThat(valid).isTrue();
    }

    @Test
    void validateToken_should_throw_exception() {
        //Arrange
        UserDetails userDetails = new User("username", "password",
                new ArrayList<>());

        //Act
        String token = "azertyuiodsfkjdsfkjsldkalmdùqsldfgk,bnjdlfskmùqzdfsdfomlkdfgdg";
        Assertions.assertThrows(MalformedJwtException.class, () -> {
            authenticationTokenService.validateToken(token, userDetails);
        });

        //Assert

    }

    private static class JwtPropertiesFake extends JwtProperties {
        public String getSecret() {
            return "azertyuiopdflkjsnqlfdmksf,sfd,";
        }
    }
}
