package com.foreignlove.common.exception;

public class DeleteFailException extends RuntimeException {
    private final static String DEFAULT_MESSAGE = "삭제에 실패하였습니다.";

    public DeleteFailException() {
        this(DEFAULT_MESSAGE);
    }

    public DeleteFailException(String message) {
        super(message);
    }
}
