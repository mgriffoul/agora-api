package com.griffoul.mathieu.agora.api.data.data.repository;


import com.griffoul.mathieu.agora.api.data.data.model.AgoraUser;

public interface IUserRepository {

    AgoraUser getUserByMail(String mail);

    void createUser(AgoraUser agoraUser);

    AgoraUser updateUser(AgoraUser agoraUser);
}
