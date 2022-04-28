package com.foreignlove.common.exception;

public class UpdateFailException extends RuntimeException {
    private final static String DEFAULT_MESSAGE = "수정에 실패하였습니다.";

    public UpdateFailException() {
        this(DEFAULT_MESSAGE);
    }

    public UpdateFailException(String message) {
        super(message);
    }
}
