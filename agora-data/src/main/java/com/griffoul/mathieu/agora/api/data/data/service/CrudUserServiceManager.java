package com.griffoul.mathieu.agora.api.data.data.service;


import com.griffoul.mathieu.agora.api.data.data.model.AgoraUser;
import com.griffoul.mathieu.agora.api.data.data.repository.IUserRepository;
import com.griffoul.mathieu.agora.api.domain.model.User;
import com.griffoul.mathieu.agora.api.domain.port.data.UserManagerAdapter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CrudUserServiceManager implements UserManagerAdapter {

    IUserRepository userRepository;

    @Autowired
    public CrudUserServiceManager(IUserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User updateUser(final User userToUpdate) {
        AgoraUser oldAgoraUser = userRepository.getUserByMail(userToUpdate.getMail());
        AgoraUser newAgoraUser = new AgoraUser();
        newAgoraUser.setMail(userToUpdate.getMail());
        newAgoraUser.setUsername(userToUpdate.getUsername());
        newAgoraUser.setId(oldAgoraUser.getId());
        newAgoraUser.setSessionHash(oldAgoraUser.getSessionHash());
        newAgoraUser.setPassword(oldAgoraUser.getPassword());
        return mapToUser(userRepository.updateUser(newAgoraUser));
    }

    @Override
    public User getUserByMail(String mail) {
        return mapToUser(userRepository.getUserByMail(mail));
    }

    private User mapToUser (AgoraUser agoraUser){
        User user = new User();
        user.setUsername(agoraUser.getUsername());
        user.setMail(agoraUser.getMail());
        return user;
    }
}
