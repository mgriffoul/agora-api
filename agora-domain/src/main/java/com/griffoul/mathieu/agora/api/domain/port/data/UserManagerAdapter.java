package com.griffoul.mathieu.agora.api.domain.port.data;

import com.griffoul.mathieu.agora.api.domain.model.User;

public interface UserManagerAdapter {

    User updateUser(User user);

    User getUserByMail(String mail);

}
