package com.foreignlove.user.service;

import com.foreignlove.user.dto.UserLoginRequest;
import com.foreignlove.user.dto.UserRegisterRequest;
import com.foreignlove.user.model.User;

import java.util.UUID;

public interface UserService {
    User add(UserRegisterRequest request);

    User getById(UUID id);

    User login(UserLoginRequest request);
}
