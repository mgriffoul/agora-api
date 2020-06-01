package com.griffoul.mathieu.agora.api.authentication.service;

import com.griffoul.mathieu.agora.api.authentication.exception.AuthenticationException;
import com.griffoul.mathieu.agora.api.authentication.exception.BddTechnicalErrorException;
import com.griffoul.mathieu.agora.api.authentication.model.AuthenticationUser;
import com.griffoul.mathieu.agora.api.authentication.model.SignUpErrorMessage;
import com.griffoul.mathieu.agora.api.authentication.model.SignUpRequest;
import com.griffoul.mathieu.agora.api.infra.data.model.AgoraUser;
import com.griffoul.mathieu.agora.api.infra.data.repository.IUserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

@Service
public class AgoraUserService {

    private final IUserRepository userRepository;
    private final AuthenticationManager authenticationManager;
    private final Logger logger = LoggerFactory.getLogger(AgoraUserService.class);
    private final PasswordService passwordService;

    @Autowired
    public AgoraUserService(final IUserRepository userRepository,
                            final AuthenticationManager authenticationManager,
                            final PasswordService passwordService) {
        this.userRepository = userRepository;
        this.authenticationManager = authenticationManager;
        this.passwordService = passwordService;
    }

    public AgoraUser getUserByMail(final String mail) {
        return userRepository.getUserByMail(mail);
    }

    public AuthenticationUser createUser(final SignUpRequest signUpRequest)
            throws AuthenticationException, BddTechnicalErrorException {
        AgoraUser agoraUser = new AgoraUser();
        agoraUser.setUsername(signUpRequest.getUsername());
        agoraUser.setMail(signUpRequest.getMail());
        String seed = passwordService.getRandomSeed();
        agoraUser.setSessionHash(seed);
        agoraUser.setPassword(passwordService.createPassword(seed, signUpRequest.getPassword()));
        try {
            userRepository.createUser(agoraUser);
        } catch (DataIntegrityViolationException e) {
            analyseException(e);
        } catch (Exception e) {
            logger.error(e + "Problem during user signUp : database seems to be unavailable");
            throw new BddTechnicalErrorException("ERROR : database temporarily unavailable");
        }
        logger.info("User successfully created : {}", agoraUser.getUsername());
        return mapToSignedUpUser(agoraUser);
    }

    public void authenticate(String username, String password) throws AuthenticationException {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        } catch (DisabledException e) {
            throw new AuthenticationException("User has been disabled");
        } catch (BadCredentialsException e) {
            throw new AuthenticationException("Invalid Token");
        }
    }

    private void analyseException(DataIntegrityViolationException e) throws AuthenticationException {
        logger.error("Problem during user signUp : {}", e.getMessage());
        if (e.getMessage() != null && e.getMessage().contains("mail")) {
            throw new AuthenticationException(SignUpErrorMessage.MAIL_UNAVAILABLE.name());
        }
        if (e.getMessage() != null && e.getMessage().contains("username")) {
            throw new AuthenticationException(SignUpErrorMessage.USERNAME_UNAVAILABLE.name());
        }
    }

    private AuthenticationUser mapToSignedUpUser(AgoraUser agoraUser) {
        return new AuthenticationUser().withMail(agoraUser.getMail())
                .withUsername(agoraUser.getUsername());
    }

}
