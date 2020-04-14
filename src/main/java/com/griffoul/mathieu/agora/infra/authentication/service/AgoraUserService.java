package com.griffoul.mathieu.agora.infra.authentication.service;

import com.griffoul.mathieu.agora.infra.authentication.exception.AuthenticationException;
import com.griffoul.mathieu.agora.infra.authentication.model.SignUpRequest;
import com.griffoul.mathieu.agora.infra.authentication.model.SignedUpUser;
import com.griffoul.mathieu.agora.infra.authentication.properties.AuthenticationProperties;
import com.griffoul.mathieu.agora.infra.data.model.AgoraUser;
import com.griffoul.mathieu.agora.infra.data.repository.IUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
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

    public SignedUpUser createUser(final SignUpRequest signUpRequest) throws AuthenticationException {
        AgoraUser agoraUser = new AgoraUser();
        agoraUser.setUsername(signUpRequest.getUsername());
        agoraUser.setMail(signUpRequest.getMail());
        String seed = SeedService.getRandomSeed();
        agoraUser.setSessionHash(seed);
        agoraUser.setPassword(createPassword(seed, signUpRequest.getPassword()));
        try {
            userRepository.createUser(agoraUser);
        }catch (DataIntegrityViolationException e){
            throw new AuthenticationException(e.getMessage(), e);
        }
        return mapToSignedUpUser(agoraUser);
    }

    private SignedUpUser mapToSignedUpUser(AgoraUser agoraUser) {
        return new SignedUpUser().withMail(agoraUser.getMail())
                .withUsername(agoraUser.getUsername());
    }

    private String createPassword(String seed, String rawPassword) {
        return bCryptPasswordEncoder.encode(seed + rawPassword + authenticationProperties.getPasswordSeed());
    }

}
