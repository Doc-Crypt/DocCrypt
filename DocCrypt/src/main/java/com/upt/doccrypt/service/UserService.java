package com.upt.doccrypt.service;


import com.upt.doccrypt.model.user.User;

import java.util.List;

public interface UserService {

//    User register(User user);
    List<User> getAll();
    User findByUsername(String username);
    Boolean containUserByEmail(String email);
    User getUserByEmail(String email);
}
