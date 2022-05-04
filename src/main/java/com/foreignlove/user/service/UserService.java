package com.foreignlove.user.service;

import com.foreignlove.user.dto.UserLoginRequest;
import com.foreignlove.user.model.User;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

public interface UserService {
    User add(String name, String email, String password, String nickname, UUID schoolId, MultipartFile file);

    User getById(UUID id);

    User login(UserLoginRequest request);
}
