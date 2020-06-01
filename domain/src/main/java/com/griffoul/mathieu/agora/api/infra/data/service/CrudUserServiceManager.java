package com.griffoul.mathieu.agora.api.infra.data.service;


import com.griffoul.mathieu.agora.api.domain.model.User;
import com.griffoul.mathieu.agora.api.domain.port.data.UserManagerAdapter;
import com.griffoul.mathieu.agora.api.infra.data.model.AgoraUser;
import com.griffoul.mathieu.agora.api.infra.data.repository.IUserRepository;
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
    public User updateUser(User user) {
        AgoraUser agoraUser = mapToAgoraUser(user);
        AgoraUser updatedAgoraUser = userRepository.updateUser(agoraUser);
        return mapToUser(updatedAgoraUser);
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
