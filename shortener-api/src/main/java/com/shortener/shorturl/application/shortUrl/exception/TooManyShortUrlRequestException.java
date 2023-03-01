package com.shortener.shorturl.application.shortUrl.exception;

/**
 * 다수의 단축 URL 생성 요청시, 요청 갯수가 많을 때 던져지는 예외
 */
public class TooManyShortUrlRequestException extends RuntimeException {

    public TooManyShortUrlRequestException(String message) {
        super(message);
    }
}
