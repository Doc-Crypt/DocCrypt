package com.upt.doccrypt.service;


import com.upt.doccrypt.model.User;

import java.util.List;

public interface UserService {

    User register(User user, Boolean isNotary);

    List<User> getAll();

    User findByUsername(String username);

    User findById(Long id);

    void delete(Long id);

    Boolean containUserByEmail(String email);

    User getUserByEmail(String email);
}
