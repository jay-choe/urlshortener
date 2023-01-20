package com.shortener.shorturl.application.shortUrl.service.generator;

/**
 * Short URL Generate Strategy Interface.
 *
 */
public interface ShortURLGenerateStrategy {
    String create(String originalURL);
    String create();
}
