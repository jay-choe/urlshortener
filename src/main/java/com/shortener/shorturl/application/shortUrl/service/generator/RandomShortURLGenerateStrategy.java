package com.shortener.shorturl.application.shortUrl.service.generator;

import com.aventrix.jnanoid.jnanoid.NanoIdUtils;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public class RandomShortURLGenerateStrategy implements ShortURLGenerateStrategy {

    private static final int defaultSize = 5;

    @Override
    public String create(String originalURL) {
        return NanoIdUtils.randomNanoId().substring(0, defaultSize);
    }
}
