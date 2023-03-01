package com.shortener.common.request;

import java.util.List;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class MultiShortUrlRequest {
    private List<ShortUrlRequest> urlList;

    @Getter
    @Builder
    public static class ShortUrlRequest {
        private String id;
        private String originalUrl;
    }
}
