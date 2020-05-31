package com.griffoul.mathieu.agora.domain.user.service;


import com.griffoul.mathieu.agora.domain.user.model.User;
import com.griffoul.mathieu.agora.domain.user.port.application.UserAdapter;
import com.griffoul.mathieu.agora.domain.user.port.data.UserManagerAdapter;
import com.griffoul.mathieu.agora.infra.data.model.AgoraUser;
import com.griffoul.mathieu.agora.infra.data.repository.IUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService implements UserAdapter {

    private final IUserRepository userRepository;
    private final UserManagerAdapter userManagerAdapter;

    @Autowired
    public UserService(IUserRepository userRepository, UserManagerAdapter userManagerAdapter) {
        this.userRepository = userRepository;
        this.userManagerAdapter = userManagerAdapter;
    }

    public User getUserByMail(String mail) {
       AgoraUser agoraUser = userRepository.getUserByMail(mail);
       User user = new User();
       user.setId(agoraUser.getId());
       user.setMail(agoraUser.getMail());
       user.setUsername(agoraUser.getUsername());
        return user;
    }

    public User updateUser(User user) {
        userManagerAdapter.updateUser(user);
        return null;
    }

}
