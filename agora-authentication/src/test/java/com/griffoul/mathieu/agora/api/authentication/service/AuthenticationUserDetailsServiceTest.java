package com.griffoul.mathieu.agora.api.authentication.service;

import com.griffoul.mathieu.agora.api.data.data.model.AgoraUser;
import com.griffoul.mathieu.agora.api.data.data.repository.IUserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;

@ExtendWith(MockitoExtension.class)
class AuthenticationUserDetailsServiceTest {

    @InjectMocks
    private AuthenticationUserDetailsService authenticationUserDetailsService;

    @Mock
    private IUserRepository userRepository;

    @Test
    void loadUserByUsername_should_return_user_details() {
        //Arrange
        AgoraUser agoraUser = new AgoraUser();
        agoraUser.setPassword("password");
        agoraUser.setMail("mail");

        Mockito.when(userRepository.getUserByMail(any())).thenReturn(agoraUser);

        //Act
        UserDetails userDetails = authenticationUserDetailsService.loadUserByUsername("mail");

        //Assert
        assertThat(userDetails.getUsername()).isEqualTo("mail");
        assertThat(userDetails.getPassword()).isEqualTo("password");

    }

    @Test
    void loadUserByUsername_should_throw_UsernameNotFoundException() {
        //Arrange
        Mockito.when(userRepository.getUserByMail(any())).thenReturn(null);

        //Act
        assertThrows(UsernameNotFoundException.class, () -> {
            authenticationUserDetailsService.loadUserByUsername("mail");
        });
        //Assert
    }
}
