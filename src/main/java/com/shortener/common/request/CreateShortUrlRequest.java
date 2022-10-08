package com.shortener.common.request;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CreateShortUrlRequest {
    private String originalUrl;
}
