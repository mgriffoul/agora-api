package com.griffoul.mathieu.agora.domain.user.port.data;

import com.griffoul.mathieu.agora.domain.user.model.User;

public interface UserManagerAdapter {

    User updateUser(User user);

    User getUserByMail(String mail);

}
