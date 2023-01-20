package com.shortener.shorturl.application.shortUrl.service;

import com.shortener.shorturl.application.shortUrl.dto.command.CreateCustomUrlCommand;
import com.shortener.shorturl.application.shortUrl.dto.command.CreateShortUrlCommand;
import com.shortener.shorturl.application.shortUrl.dto.command.CreateShortUrlListCommand;
import com.shortener.shorturl.application.shortUrl.dto.response.ShortUrlListResponse;
import com.shortener.shorturl.application.shortUrl.exception.TooManyShortUrlRequestException;
import com.shortener.shorturl.domain.urlShortener.url.Url;
import com.shortener.shorturl.infrastructure.cache.CacheService;
import javax.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ShortenerFacade {
    private final ShortenerService service;
    private final CacheService<Url> cacheService;

    public String getRedirectUrl(String shortValue) {
        // TODO cache -> AOP
        if (cacheService.exist(shortValue))
            return cacheService.get(shortValue).getOriginalUrl();
        Url originalUrl = service.findOriginalUrl(shortValue);
        cacheService.set(originalUrl, 60);  // update?
        return originalUrl.getOriginalUrl();
    }
    @Transactional
    public String createCustomUrl(CreateCustomUrlCommand command) {
        String originUrl = command.getOriginUrl();
        String target = command.getTarget();

        service.checkAlreadyExistAddress(target);
        return service.createCustomUrl(originUrl, target);
    }

    @Transactional
    public ShortUrlListResponse createShortUrlByMultiRequest(CreateShortUrlListCommand command) {
        if (command.getSize() > 10) throw new TooManyShortUrlRequestException("Too Many Short Url Request, Recommended: less than 10");
        return service.createShortUrlList(command.getUrlList());
    }

    @Transactional
    public String createFixedShortURL(CreateShortUrlCommand command) {
        String originalUrl = command.getOriginalUrl();
        return service.createFixedShortURL(originalUrl);
    }

    public String createRandomURL() {
        return service.createRandomUrl();
    }
}
