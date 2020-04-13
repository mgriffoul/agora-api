package com.griffoul.mathieu.agora.infra.data.repository;

import com.griffoul.mathieu.agora.infra.authentication.model.AuthenticationSignUpRequest;
import com.griffoul.mathieu.agora.infra.data.model.AgoraUser;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import java.util.List;

@Service
public class UserRepository implements IUserRepository {

    private EntityManager entityManager;

    public UserRepository(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    private final static String GET_AGORA_USER_BY_USERNAME = "SELECT a FROM AgoraUser a WHERE a.username = :username";

    @Override
    @Transactional
    public AgoraUser getUserByUsername(String username) {
        Query q;
        q = entityManager
                .createQuery(GET_AGORA_USER_BY_USERNAME, AgoraUser.class)
                .setParameter("username", username);
        return (AgoraUser) q.getSingleResult();
    }

    @Override
    @Transactional
    public void createUser(AgoraUser agoraUser) {
        entityManager.persist(agoraUser);
    }

}
