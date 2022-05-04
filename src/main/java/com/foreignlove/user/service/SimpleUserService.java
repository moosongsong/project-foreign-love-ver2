package com.foreignlove.user.service;

import com.foreignlove.infra.s3.service.S3FileIOManager;
import com.foreignlove.school.model.School;
import com.foreignlove.school.repository.SchoolRepository;
import com.foreignlove.user.dto.UserLoginRequest;
import com.foreignlove.user.model.User;
import com.foreignlove.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class SimpleUserService implements UserService {
    private final UserRepository userRepository;
    private final SchoolRepository schoolRepository;
    private final S3FileIOManager s3FileIOManager;

    @Override
    @Transactional
    public User add(String name, String email, String password, String nickname, UUID schoolId, MultipartFile file) {
        School school = schoolRepository.findById(schoolId);
        String imageUrl = null;
        if (!file.isEmpty()) imageUrl = s3FileIOManager.uploadImage(file);
        User user = new User(email, name, password, school, nickname, imageUrl);
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
