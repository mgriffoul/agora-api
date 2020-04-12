package com.griffoul.mathieu.agora.infra.authentication.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class AuthenticationUserDetailsService implements UserDetailsService {

    private Map<String, String> authorizedCredentials = getCredentials();

    private PasswordEncoder userDetailsServicePasswordEncoder;

    @Autowired
    public AuthenticationUserDetailsService(PasswordEncoder userDetailsServicePasswordEncoder) {
        this.userDetailsServicePasswordEncoder = userDetailsServicePasswordEncoder;
    }

    @Override
    public UserDetails loadUserByUsername(String username) {
        if (authorizedCredentials.containsKey(username)) {
            return new User(username, userDetailsServicePasswordEncoder.encode(authorizedCredentials.get(username)),
                    new ArrayList<>());
        } else {
            throw new UsernameNotFoundException("User not found with username: " + username);
        }
    }

    private Map<String, String> getCredentials() {
        Map<String, String> map = new HashMap<>();
        map.put("javainuse", "password");
        map.put("toto", "tata");
        map.put("griff", "1234");
        return map;
    }

}
