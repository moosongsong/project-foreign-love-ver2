package com.foreignlove.user.dto;

public record UserLoginRequest(
    String email,
    String password
) {}