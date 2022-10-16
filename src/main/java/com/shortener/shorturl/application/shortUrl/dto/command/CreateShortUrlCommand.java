package com.shortener.shorturl.application.shortUrl.dto.command;

import com.shortener.common.request.CreateShortUrlRequest;
import lombok.Getter;

@Getter
public class CreateShortUrlCommand {
    private final String originalUrl;

    private CreateShortUrlCommand(String originalUrl) {
        this.originalUrl = originalUrl;
    }

    public static CreateShortUrlCommand of(CreateShortUrlRequest request) {
        return new CreateShortUrlCommand(request.getOriginalUrl());
    }
}
