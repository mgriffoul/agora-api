package com.griffoul.mathieu.agora.api.infra.service;


import com.griffoul.mathieu.agora.api.infra.authentication.exception.AuthenticationException;
import com.griffoul.mathieu.agora.api.infra.authentication.exception.BddTechnicalErrorException;
import com.griffoul.mathieu.agora.api.infra.authentication.model.AuthenticationUser;
import com.griffoul.mathieu.agora.api.infra.authentication.model.SignUpRequest;
import com.griffoul.mathieu.agora.api.infra.authentication.service.AgoraUserService;
import com.griffoul.mathieu.agora.api.infra.authentication.service.PasswordService;
import com.griffoul.mathieu.agora.api.infra.data.model.AgoraUser;
import com.griffoul.mathieu.agora.api.infra.data.repository.IUserRepository;
import org.hibernate.HibernateException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.DataIntegrityViolationException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AgoraUserServiceTest {

    @Spy
    private IUserRepository userRepository;
    @InjectMocks
    private AgoraUserService agoraUserService;
    @Mock
    private PasswordService passwordService;

    @Test
    void createUser_should_ask_user_dataBase_creation_and_return_new_SignedUpUser() throws AuthenticationException, BddTechnicalErrorException {
        //Arrange
        when(passwordService.getRandomSeed()).thenReturn("retY5sqW");
        when(passwordService.createPassword(any(), any())).thenReturn("$2a$10$OR9MfmvP05T4cLjZ.JrTae1mv.3bR0PmpTsHm6LfantkxCrcHzFFu");
        doNothing().when(userRepository).createUser(any());
        SignUpRequest signUpRequest = new SignUpRequest();
        signUpRequest.setMail("batman@gotham.goov");
        signUpRequest.setUsername("bruce wayne");
        signUpRequest.setPassword("iloveJoker");

        //Act
        AuthenticationUser signedUpUser = agoraUserService.createUser(signUpRequest);

        //Assert
        verify(userRepository, times(1)).createUser(any());
        assertThat(signedUpUser.getUsername()).isEqualTo("bruce wayne");
        assertThat(signedUpUser.getMail()).isEqualTo("batman@gotham.goov");

    }

    @Test
    void createUser_should_throw_AuthenticationException_with_mail_error_message() throws AuthenticationException, BddTechnicalErrorException {
        //Arrange
        when(passwordService.getRandomSeed()).thenReturn("retY5sqW");
        when(passwordService.createPassword(any(), any())).thenReturn("$2a$10$OR9MfmvP05T4cLjZ.JrTae1mv.3bR0PmpTsHm6LfantkxCrcHzFFu");
        doThrow(new DataIntegrityViolationException("zpefjsdf mail pfgijdfg")).when(userRepository).createUser(any());
        SignUpRequest signUpRequest = new SignUpRequest();

        //Act
        Exception exception = Assertions.assertThrows(AuthenticationException.class, () -> {
            agoraUserService.createUser(signUpRequest);
        });

        //Assert
        assertThat(exception.getMessage()).isEqualTo("MAIL_UNAVAILABLE");
    }

    @Test
    void createUser_should_throw_AuthenticationException_with_username_error_message() throws AuthenticationException, BddTechnicalErrorException {
        //Arrange
        when(passwordService.getRandomSeed()).thenReturn("retY5sqW");
        when(passwordService.createPassword(any(), any())).thenReturn("$2a$10$OR9MfmvP05T4cLjZ.JrTae1mv.3bR0PmpTsHm6LfantkxCrcHzFFu");
        doThrow(new DataIntegrityViolationException("zpefjsdf username pfgijdfg")).when(userRepository).createUser(any());
        SignUpRequest signUpRequest = new SignUpRequest();

        //Act
        Exception exception = Assertions.assertThrows(AuthenticationException.class, () -> {
            agoraUserService.createUser(signUpRequest);
        });

        //Assert
        assertThat(exception.getMessage()).isEqualTo("USERNAME_UNAVAILABLE");
    }

    @Test
    void createUser_should_throw_BddTechnicalErrorException_with_database_error_message() throws AuthenticationException, BddTechnicalErrorException {
        //Arrange
        when(passwordService.getRandomSeed()).thenReturn("retY5sqW");
        when(passwordService.createPassword(any(), any())).thenReturn("$2a$10$OR9MfmvP05T4cLjZ.JrTae1mv.3bR0PmpTsHm6LfantkxCrcHzFFu");
        doThrow(new HibernateException("")).when(userRepository).createUser(any());
        SignUpRequest signUpRequest = new SignUpRequest();

        //Act
        Exception exception = Assertions.assertThrows(BddTechnicalErrorException.class, () -> {
            agoraUserService.createUser(signUpRequest);
        });

        //Assert
        assertThat(exception.getMessage()).isEqualTo("ERROR : database temporarily unavailable");
    }

    @Test
    void getUSerByMail_should_return_AgoraUser() {
        // Arrange
        AgoraUser agoraUser = new AgoraUser();
        agoraUser.setMail("mail@mail.com");
        agoraUser.setUsername("username");
        String mail = "toto@gmail.com";
        when(userRepository.getUserByMail(anyString())).thenReturn(agoraUser);

        // Act
        AgoraUser agoraUserToTest = agoraUserService.getUserByMail(mail);

        // Assert
        assertThat(agoraUserToTest.getMail()).isEqualTo(agoraUser.getMail());
        assertThat(agoraUserToTest.getUsername()).isEqualTo(agoraUser.getUsername());
        verify(userRepository, times(1)).getUserByMail("toto@gmail.com");
    }
}
