package com.griffoul.mathieu.agora.infra.data.repository;

import com.griffoul.mathieu.agora.infra.data.model.AgoraUser;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;

@Service
public class UserRepository implements IUserRepository {

    private EntityManager entityManager;

    public UserRepository(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    private final static String GET_AGORA_USER_BY_USERNAME = "SELECT a FROM AgoraUser a WHERE a.username = :username";

    @Override
    @Transactional
    @Cacheable(value = "userCache")
    public AgoraUser getUserByUsername(final String username) {
        return entityManager
                .createQuery(GET_AGORA_USER_BY_USERNAME, AgoraUser.class)
                .setParameter("username", username)
                .getSingleResult();
    }

    @Override
    @Transactional
    public void createUser(final AgoraUser agoraUser) {
        entityManager.persist(agoraUser);
    }

}
