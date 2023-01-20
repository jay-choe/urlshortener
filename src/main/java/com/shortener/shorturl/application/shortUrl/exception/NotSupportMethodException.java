package com.shortener.shorturl.application.shortUrl.exception;

/**
 * Thrown when method Not Supported
 *
 */
public class NotSupportMethodException extends RuntimeException {

    public NotSupportMethodException(String ctx) {
        super(String.format("%s Method Not Supported", ctx));
    }
}
