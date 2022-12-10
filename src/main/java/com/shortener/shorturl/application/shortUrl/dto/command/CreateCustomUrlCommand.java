package com.shortener.shorturl.application.shortUrl.dto.command;


import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@Builder
@Getter
@EqualsAndHashCode
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
