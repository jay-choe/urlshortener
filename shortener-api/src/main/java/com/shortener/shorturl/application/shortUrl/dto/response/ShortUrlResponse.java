package com.shortener.shorturl.application.shortUrl.dto.response;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Builder
@EqualsAndHashCode
@Getter
@ToString
public class ShortUrlResponse {
    private String originalUrl;
    private String shortUrl;
}
