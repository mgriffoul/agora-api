package com.griffoul.mathieu.agora.infra.data.repository;

import com.griffoul.mathieu.agora.infra.data.model.AgoraUser;

public interface IUserRepository {

    AgoraUser getUserByMail(String mail);

    void createUser(AgoraUser agoraUser);

}
