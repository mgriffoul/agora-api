package com.griffoul.mathieu.agora.infra.data.repository;

import com.griffoul.mathieu.agora.infra.data.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
}
