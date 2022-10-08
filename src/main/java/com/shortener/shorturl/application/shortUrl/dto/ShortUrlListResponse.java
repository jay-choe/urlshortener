package com.shortener.shorturl.application.shortUrl.dto;

import java.util.List;
import lombok.Builder;
import lombok.EqualsAndHashCode;

@Builder
public class ShortUrlListResponse {
    private List<UrlWithIdentifier> urlList;

    @Builder
    @EqualsAndHashCode
    public static class UrlWithIdentifier {
        private String id;
        private String shortUrl;
    }

}
