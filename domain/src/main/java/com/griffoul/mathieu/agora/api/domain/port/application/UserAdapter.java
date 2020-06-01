package com.griffoul.mathieu.agora.api.domain.port.application;

import com.griffoul.mathieu.agora.api.domain.model.User;

public interface UserAdapter {

    User getUserByMail(final String mail);

    User updateUser(final User user);

}
