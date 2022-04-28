package com.foreignlove.common.exception;

public class InvalidParameterException extends RuntimeException {
    private final static String DEFAULT_MESSAGE = "잘못된 입력값입니다.";

    public InvalidParameterException() {
        this(DEFAULT_MESSAGE);
    }

    public InvalidParameterException(String message) {
        super(message);
    }
}
