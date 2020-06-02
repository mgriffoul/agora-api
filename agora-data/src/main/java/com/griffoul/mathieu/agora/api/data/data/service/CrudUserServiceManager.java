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
    public User updateUser(final User user) {
        AgoraUser oldAgoraUser = userRepository.getUserByMail(user.getMail());
        AgoraUser newUserToUpdate = new AgoraUser();
        newUserToUpdate.setMail(user.getMail());
        newUserToUpdate.setUsername(user.getUsername());
        newUserToUpdate.setId(userRepository.getUserByMail(user.getMail()).getId());
        newUserToUpdate.setSessionHash(oldAgoraUser.getSessionHash());
        newUserToUpdate.setPassword(oldAgoraUser.getPassword());
        return mapToUser(userRepository.updateUser(newUserToUpdate));
    }

    @Override
    public User getUserByMail(String mail) {
        return mapToUser(userRepository.getUserByMail(mail));
    }

    private AgoraUser mapToAgoraUser (User user) {
        AgoraUser agoraUser = new AgoraUser();
        agoraUser.setMail(user.getMail());
        agoraUser.setUsername(user.getUsername());
        return agoraUser;
    }

    private User mapToUser (AgoraUser agoraUser){
        User user = new User();
        user.setUsername(agoraUser.getUsername());
        user.setMail(agoraUser.getMail());
        return user;
    }
}
