package com.foreignlove.common.exception;

public class SaveFailException extends RuntimeException {
    private final static String DEFAULT_MESSAGE = "저장에 실패하였습니다.";

    public SaveFailException() {
        this(DEFAULT_MESSAGE);
    }

    public SaveFailException(String message) {
        super(message);
    }
}
