package com.griffoul.mathieu.agora.api.authentication.service;

import com.griffoul.mathieu.agora.api.infra.data.model.AgoraUser;
import com.griffoul.mathieu.agora.api.infra.data.repository.IUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

import static java.util.Objects.nonNull;

@Service
public class AuthenticationUserDetailsService implements UserDetailsService {

    private IUserRepository userRepository;

    @Autowired
    public AuthenticationUserDetailsService(final IUserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(final String mail) {
        AgoraUser agoraUser = userRepository.getUserByMail(mail);
        if (nonNull(agoraUser)) {
            return new User(agoraUser.getMail(), agoraUser.getPassword(),
                    new ArrayList<>());
        } else {
            throw new UsernameNotFoundException("User not found with this mail: " + mail);
        }
    }

}
