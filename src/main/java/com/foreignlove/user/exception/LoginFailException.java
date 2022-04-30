package com.foreignlove.user.exception;

public class LoginFailException extends RuntimeException {
    private static final String DEFAULT_MESSAGE = "인가되지 않은 사용자 입니다.";

    public LoginFailException() {
        this(DEFAULT_MESSAGE);
    }

    public LoginFailException(String message) {
        super(message);
    }
}
