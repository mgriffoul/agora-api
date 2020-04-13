package com.griffoul.mathieu.agora.infra.authentication.service;

import com.griffoul.mathieu.agora.infra.data.repository.IUserRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.SecureRandom;
import java.util.HashMap;
import java.util.Map;

@Service
public class AuthenticationUserDetailsService implements UserDetailsService {

    private Map<String, String> authorizedCredentials = getCredentials();

    private BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    private IUserRepository userRepository;

    public AuthenticationUserDetailsService(IUserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Value("${jwt.secret}")
    private static String fixSalt;

    @Override
    public UserDetails loadUserByUsername(String username) {

        userRepository.getUserByUsername(username);


        return null;
    }

    public static void main(String[] args) {
        String username = "";
        String password = "";
        try {
            BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
            SecureRandom secureRandom = SecureRandom.getInstance("SHA1PRNG", "SUN");

            String rawPassword = "password";
            int salt = secureRandom.nextInt();
            String encodedPassword = encoder.encode(salt + rawPassword + fixSalt);

            System.out.println("raw pass : " + rawPassword);
            System.out.println("salt : " + salt);
            System.out.println("encoded : " + encodedPassword);

            System.out.println("Match : " + encoder.matches(salt + rawPassword + fixSalt, encodedPassword));


        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (NoSuchProviderException e) {
            e.printStackTrace();
        }

    }

//    @Override
//    public UserDetails loadUserByUsername(String username) {
//
//        if (authorizedCredentials.containsKey(username)) {
//            return new User(username, encoder.encode(authorizedCredentials.get(username)),
//                    new ArrayList<>());
//        } else {
//            throw new UsernameNotFoundException("User not found with username: " + username);
//        }
//    }

    private Map<String, String> getCredentials() {
        Map<String, String> map = new HashMap<>();
        map.put("javainuse", "password");
        map.put("toto", "tata");
        map.put("griff", "1234");
        return map;
    }

}
