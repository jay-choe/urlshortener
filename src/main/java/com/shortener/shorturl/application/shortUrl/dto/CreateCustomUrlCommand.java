package com.shortener.shorturl.application.shortUrl.dto;


import lombok.Builder;
import lombok.Getter;
@Builder
@Getter
public class CreateCustomUrlCommand {
    private String originUrl;
    private String target;

    public static CreateCustomUrlCommand of(String originUrl, String target) {
        return CreateCustomUrlCommand.builder()
            .originUrl(originUrl)
            .target(target)
            .build();
    }
}
