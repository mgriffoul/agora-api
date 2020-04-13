package com.griffoul.mathieu.agora.infra.data.repository;

import com.griffoul.mathieu.agora.infra.data.model.User;

public interface IUserRepository {

    User getUserByUsername(String username);

}
