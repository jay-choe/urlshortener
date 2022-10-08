package com.shortener.shorturl.application.shortUrl.service;

import com.shortener.shorturl.application.shortUrl.dto.CreateCustomUrlCommand;
import com.shortener.shorturl.application.shortUrl.dto.CreateShortUrlListCommand;
import com.shortener.shorturl.application.shortUrl.dto.ShortUrlListResponse;
import com.shortener.shorturl.application.shortUrl.exception.TooManyShortUrlRequestException;
import javax.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ShortenerFacade {
    private final ShortenerService service;

    public String getRedirectUrl(String shortValue) {
        return service.findOriginalUrl(shortValue);
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

}
