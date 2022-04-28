package com.foreignlove.common.exception;

public class FindFailException extends RuntimeException {
    private final static String DEFAULT_MESSAGE = "조회에 실패하였습니다.";

    public FindFailException() {
        this(DEFAULT_MESSAGE);
    }

    public FindFailException(String message) {
        super(message);
    }
}
