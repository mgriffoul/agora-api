package com.griffoul.mathieu.agora.api.authentication.service;

import com.google.common.hash.Hashing;
import com.griffoul.mathieu.agora.api.authentication.properties.AuthenticationProperties;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.SecureRandom;

@Service
public class PasswordService {

    private AuthenticationProperties authenticationProperties;
    private BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

    public PasswordService(AuthenticationProperties authenticationProperties) {
        this.authenticationProperties = authenticationProperties;
    }

    public String getRandomSeed() {
        SecureRandom secureRandom = new SecureRandom();
        byte[] bytes = new byte[8];
        secureRandom.nextBytes(bytes);
        BigInteger number = new BigInteger(1, bytes);
        return Hashing.md5()
                .hashString(number.toString(16), StandardCharsets.UTF_8)
                .toString();
    }

    public String createPassword(String seed, String rawPassword) {
        return bCryptPasswordEncoder.encode(seed + rawPassword + authenticationProperties.getPasswordSeed());
    }

}
