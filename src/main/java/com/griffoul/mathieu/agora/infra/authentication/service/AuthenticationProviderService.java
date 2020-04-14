package com.griffoul.mathieu.agora.infra.authentication.service;

import com.griffoul.mathieu.agora.infra.authentication.properties.AuthenticationProperties;
import com.griffoul.mathieu.agora.infra.data.model.AgoraUser;
import com.griffoul.mathieu.agora.infra.data.repository.IUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

import static java.util.Objects.nonNull;

@Service
public class AuthenticationProviderService implements AuthenticationProvider {

    private IUserRepository userRepository;
    private AuthenticationProperties authenticationProperties;
    private BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

    @Autowired
    public AuthenticationProviderService(final IUserRepository userRepository, final AuthenticationProperties authenticationProperties) {
        this.userRepository = userRepository;
        this.authenticationProperties = authenticationProperties;
    }

    @Override
    public Authentication authenticate(Authentication authentication) {
        String username = authentication.getPrincipal().toString();
        if (comparePasswords(authentication.getCredentials().toString(), username)) {
            return new UsernamePasswordAuthenticationToken(
                    username, authentication.getCredentials().toString(), new ArrayList<>()
            );
        } else {
            return null;
        }
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }

    private boolean comparePasswords(String password, String username) {
        AgoraUser agoraUser = userRepository.getUserByUsername(username);
        return nonNull(agoraUser) && bCryptPasswordEncoder.matches(
                agoraUser.getSessionHash() + password + authenticationProperties.getPasswordSeed(),
                agoraUser.getPassword());
    }


}
