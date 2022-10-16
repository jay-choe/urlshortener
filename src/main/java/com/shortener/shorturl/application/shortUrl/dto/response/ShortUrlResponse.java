package com.shortener.shorturl.application.shortUrl.dto.response;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Builder
@EqualsAndHashCode
@ToString
public class ShortUrlResponse {
    private String originalUrl;
    private String shortUrl;
}
