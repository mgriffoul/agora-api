package com.griffoul.mathieu.agora.domain.user.port.application;

import com.griffoul.mathieu.agora.domain.user.model.User;

public interface UserAdapter {

    User getUserByMail(final String mail);

    User updateUser(final User user);

}
