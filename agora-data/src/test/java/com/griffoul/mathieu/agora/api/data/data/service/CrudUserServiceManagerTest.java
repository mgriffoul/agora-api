package com.griffoul.mathieu.agora.api.data.data.service;

import com.griffoul.mathieu.agora.api.data.data.model.AgoraUser;
import com.griffoul.mathieu.agora.api.data.data.repository.IUserRepository;
import com.griffoul.mathieu.agora.api.domain.model.User;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CrudUserServiceManagerTest {

    @InjectMocks
    private CrudUserServiceManager crudUserServiceManager;
    @Mock
    private IUserRepository userRepository;

    @Test
    void updateUser_should_return_updated_agora_user(){
        // Arrange
        AgoraUser oldAgoraUser = new AgoraUser();
        oldAgoraUser.setPassword("password");
        oldAgoraUser.setSessionHash("sessionHash");
        oldAgoraUser.setId(1);
        when(userRepository.getUserByMail(any())).thenReturn(oldAgoraUser);
        AgoraUser newAgoraUser = new AgoraUser();
        newAgoraUser.setUsername("username2");
        newAgoraUser.setMail("mail2");
        when(userRepository.updateUser(any())).thenReturn(null);

        // Act
        User updatedUser = crudUserServiceManager.updateUser(null);

        // Assert
    }

}
