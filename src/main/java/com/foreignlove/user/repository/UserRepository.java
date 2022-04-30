package com.foreignlove.user.repository;

import com.foreignlove.user.model.User;

import java.util.UUID;

public interface UserRepository {
    User save(User user);

    User findById(UUID id);

    User findByEmailAndPassword(String email, String password);
}
