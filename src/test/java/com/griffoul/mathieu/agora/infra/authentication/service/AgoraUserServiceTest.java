package com.griffoul.mathieu.agora.infra.authentication.service;


import com.griffoul.mathieu.agora.infra.authentication.exception.AuthenticationException;
import com.griffoul.mathieu.agora.infra.authentication.exception.BddTechnicalErrorException;
import com.griffoul.mathieu.agora.infra.authentication.model.SignUpRequest;
import com.griffoul.mathieu.agora.infra.authentication.model.SignedUpUser;
import com.griffoul.mathieu.agora.infra.data.repository.IUserRepository;
import org.hibernate.HibernateException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.DataIntegrityViolationException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;

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
        Mockito.when(passwordService.getRandomSeed()).thenReturn("retY5sqW");
        Mockito.when(passwordService.createPassword(any(), any())).thenReturn("$2a$10$OR9MfmvP05T4cLjZ.JrTae1mv.3bR0PmpTsHm6LfantkxCrcHzFFu");
        Mockito.doNothing().when(userRepository).createUser(any());
        SignUpRequest signUpRequest = new SignUpRequest();
        signUpRequest.setMail("batman@gotham.goov");
        signUpRequest.setUsername("bruce wayne");
        signUpRequest.setPassword("iloveJoker");

        //Act
        SignedUpUser signedUpUser = agoraUserService.createUser(signUpRequest);

        //Assert
        verify(userRepository, Mockito.times(1)).createUser(any());
        assertThat(signedUpUser.getUsername()).isEqualTo("bruce wayne");
        assertThat(signedUpUser.getMail()).isEqualTo("batman@gotham.goov");

    }

    @Test
    void createUser_should_throw_AuthenticationException_with_mail_error_message() throws AuthenticationException, BddTechnicalErrorException {
        //Arrange
        Mockito.when(passwordService.getRandomSeed()).thenReturn("retY5sqW");
        Mockito.when(passwordService.createPassword(any(), any())).thenReturn("$2a$10$OR9MfmvP05T4cLjZ.JrTae1mv.3bR0PmpTsHm6LfantkxCrcHzFFu");
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
        Mockito.when(passwordService.getRandomSeed()).thenReturn("retY5sqW");
        Mockito.when(passwordService.createPassword(any(), any())).thenReturn("$2a$10$OR9MfmvP05T4cLjZ.JrTae1mv.3bR0PmpTsHm6LfantkxCrcHzFFu");
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
        Mockito.when(passwordService.getRandomSeed()).thenReturn("retY5sqW");
        Mockito.when(passwordService.createPassword(any(), any())).thenReturn("$2a$10$OR9MfmvP05T4cLjZ.JrTae1mv.3bR0PmpTsHm6LfantkxCrcHzFFu");
        doThrow(new HibernateException("")).when(userRepository).createUser(any());
        SignUpRequest signUpRequest = new SignUpRequest();

        //Act
        Exception exception = Assertions.assertThrows(BddTechnicalErrorException.class, () -> {
            agoraUserService.createUser(signUpRequest);
        });

        //Assert
        assertThat(exception.getMessage()).isEqualTo("ERROR : database temporarily unavailable");
    }

}
