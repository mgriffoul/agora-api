package com.griffoul.mathieu.agora.api.services.service;

import com.griffoul.mathieu.agora.api.domain.model.User;
import com.griffoul.mathieu.agora.api.domain.port.application.UserAdapter;
import com.griffoul.mathieu.agora.api.domain.port.data.UserManagerAdapter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService implements UserAdapter {

    private final UserManagerAdapter userManagerAdapter;

    @Autowired
    public UserService(UserManagerAdapter userManagerAdapter) {
        this.userManagerAdapter = userManagerAdapter;
    }

    public User getUserByMail(String mail) {
       return userManagerAdapter.getUserByMail(mail);
    }

    public User updateUser(User user) {
        userManagerAdapter.updateUser(user);
        return null;
    }

}
