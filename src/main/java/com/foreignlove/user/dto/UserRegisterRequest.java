package com.foreignlove.user.dto;

import java.util.UUID;

public record UserRegisterRequest(
    String email,
    String name,
    String password,
    UUID schoolId,
    String nickname
) {}

