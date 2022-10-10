package com.shortener.shorturl.application.shortUrl.service;

import com.shortener.shorturl.application.shortUrl.dto.CreateCustomUrlCommand;
import com.shortener.shorturl.application.shortUrl.dto.CreateShortUrlCommand;
import com.shortener.shorturl.application.shortUrl.dto.CreateShortUrlListCommand;
import com.shortener.shorturl.application.shortUrl.dto.ShortUrlListResponse;
import com.shortener.shorturl.application.shortUrl.exception.TooManyShortUrlRequestException;
import com.shortener.shorturl.domain.urlShortener.url.Url;
import com.shortener.shorturl.infrastructure.persistence.CacheService;
import javax.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ShortenerFacade {
    private final ShortenerService service;
    private final CacheService<Url> cacheService;

    public String getRedirectUrl(String shortValue) {
        if (cacheService.exist(shortValue))
            return cacheService.get(shortValue).getOriginalUrl();
        Url originalUrl = service.findOriginalUrl(shortValue);
        cacheService.set(originalUrl, 60);
        return originalUrl.getOriginalUrl();
    }
    @Transactional
    public String createCustomUrl(CreateCustomUrlCommand command) {
        String originUrl = command.getOriginUrl();
        String target = command.getTarget();

        service.checkAlreadyExistTarget(target);
        return service.createCustomUrl(originUrl, target);
    }

    @Transactional
    public ShortUrlListResponse createShortUrlByMultiRequest(CreateShortUrlListCommand command) {
        if (command.getSize() > 10) throw new TooManyShortUrlRequestException("Too Many Short Url Request, Recommended: less than 10");
        return service.createShortUrlList(command.getUrlList());
    }

    @Transactional
    public String createShortUrl(CreateShortUrlCommand command) {
        String originalUrl = command.getOriginalUrl();
        return service.createShortUrl(originalUrl);
    }
}
