package com.shortener.shorturl.application.shortUrl.exception;

/**
 * Thrown when No Url to redirect
 */
public class URLNotFoundException extends RuntimeException {

    public URLNotFoundException(String msg) {
        super(msg);
    }
}
