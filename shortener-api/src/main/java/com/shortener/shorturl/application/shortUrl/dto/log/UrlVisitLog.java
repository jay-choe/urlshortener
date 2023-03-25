package com.shortener.shorturl.application.shortUrl.dto.log;

import java.time.LocalDateTime;
import lombok.Builder;

@Builder
public class UrlVisitLog {

    // TODO : browser , country .. log
    private String shortValue;
    private String redirectUrl;
    private LocalDateTime createdAt;

}
