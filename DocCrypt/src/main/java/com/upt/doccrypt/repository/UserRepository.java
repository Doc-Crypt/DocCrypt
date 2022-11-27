package com.upt.doccrypt.repository;

import com.upt.doccrypt.model.User;
import org.springframework.data.jpa.repository.JpaRepository;


public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String name);
}
