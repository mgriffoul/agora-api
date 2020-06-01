package com.griffoul.mathieu.agora.api.infra.data.repository;


import com.griffoul.mathieu.agora.api.infra.data.model.AgoraUser;

public interface IUserRepository {

    AgoraUser getUserByMail(String mail);

    void createUser(AgoraUser agoraUser);

    AgoraUser updateUser(AgoraUser agoraUser);
}
