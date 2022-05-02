package com.foreignlove.user.service;

import com.foreignlove.school.model.School;
import com.foreignlove.school.repository.SchoolRepository;
import com.foreignlove.user.dto.UserLoginRequest;
import com.foreignlove.user.dto.UserRegisterRequest;
import com.foreignlove.user.model.User;
import com.foreignlove.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class SimpleUserService implements UserService {
    private final UserRepository userRepository;
    private final SchoolRepository schoolRepository;

    @Override
    public User add(UserRegisterRequest request) {
        School school = schoolRepository.findById(request.schoolId());
        User user = new User(request.email(), request.name(), request.password(), school, request.nickname());
        return userRepository.save(user);
    }

    @Override
    public User getById(UUID id) {
        return userRepository.findById(id);
    }

    @Override
    public User login(UserLoginRequest request) {
        return userRepository.findByEmailAndPassword(request.email(), request.password());
    }
}
