package com.griffoul.mathieu.agora.infra.data.repository;

import com.griffoul.mathieu.agora.infra.data.model.User;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;

@Service
public class UserRepository implements IUserRepository{

    private EntityManager entityManager;

    public UserRepository(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public User getUserByUsername(String username) {
        return entityManager.find(User.class, username);
    }

}
