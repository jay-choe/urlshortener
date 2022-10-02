package com.shortener.shorturl.application.shortUrl.exception;

import org.springframework.util.StringUtils;

/**
 * 단축 될 URL이 이미 존재하는 경우 던져지는 예외
 */
public class AlreadyExistException extends RuntimeException {

    private final static String defaultMessage = "이미 단축될 URL이 존재합니다";

    public AlreadyExistException() {
        this(defaultMessage);
    }
    public AlreadyExistException(String message) {
        super(StringUtils.hasText(message) ? message : defaultMessage);
    }
}
