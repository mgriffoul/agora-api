package com.griffoul.mathieu.agora.infra.authentication.service;

import com.google.common.hash.Hashing;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.SecureRandom;

public class SeedService {

    public static String getRandomSeed() {
        SecureRandom secureRandom = new SecureRandom();
        byte[] bytes = new byte[8];
        secureRandom.nextBytes(bytes);
        BigInteger number = new BigInteger(1, bytes);
        return Hashing.md5()
                .hashString(number.toString(16), StandardCharsets.UTF_8)
                .toString();
    }

}
