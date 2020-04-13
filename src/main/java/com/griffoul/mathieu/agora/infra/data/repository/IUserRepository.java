package com.griffoul.mathieu.agora.infra.data.repository;

import com.griffoul.mathieu.agora.infra.data.model.AgoraUser;

public interface IUserRepository {

    AgoraUser getUserByUsername(String username);

    void createUser(AgoraUser agoraUser);

}
