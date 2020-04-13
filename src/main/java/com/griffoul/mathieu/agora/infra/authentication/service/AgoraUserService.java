package com.griffoul.mathieu.agora.infra.authentication.service;

import com.griffoul.mathieu.agora.infra.authentication.model.AuthenticationSignUpRequest;
import com.griffoul.mathieu.agora.infra.authentication.properties.AuthenticationProperties;
import com.griffoul.mathieu.agora.infra.data.model.AgoraUser;
import com.griffoul.mathieu.agora.infra.data.repository.IUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AgoraUserService {

    private IUserRepository userRepository;
    private AuthenticationProperties authenticationProperties;
    private BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

    @Autowired
    public AgoraUserService(final IUserRepository userRepository, final AuthenticationProperties authenticationProperties) {
        this.userRepository = userRepository;
        this.authenticationProperties = authenticationProperties;
    }

    public void createUser(final AuthenticationSignUpRequest authenticationSignUpRequest) {
        AgoraUser agoraUser = new AgoraUser();
        agoraUser.setUsername(authenticationSignUpRequest.getUsername());
        agoraUser.setMail(authenticationSignUpRequest.getMail());
        String seed = SeedService.getRandomSeed();
        agoraUser.setSeed(seed);
        agoraUser.setPassword(createPassword(seed, authenticationSignUpRequest.getPassword()));
        userRepository.createUser(agoraUser);
    }

    private String createPassword(String seed, String rawPassword) {
        return bCryptPasswordEncoder.encode(seed + rawPassword + authenticationProperties.getPasswordSeed());
    }

}
