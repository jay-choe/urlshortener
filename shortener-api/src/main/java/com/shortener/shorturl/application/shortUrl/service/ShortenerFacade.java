package com.shortener.shorturl.application.shortUrl.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.shortener.shorturl.application.shortUrl.dto.command.CreateCustomUrlCommand;
import com.shortener.shorturl.application.shortUrl.dto.command.CreateShortUrlCommand;
import com.shortener.shorturl.application.shortUrl.dto.command.CreateShortUrlListCommand;
import com.shortener.shorturl.application.shortUrl.dto.log.UrlVisitLog;
import com.shortener.shorturl.application.shortUrl.dto.response.ShortUrlListResponse;
import com.shortener.shorturl.application.shortUrl.exception.TooManyShortUrlRequestException;
import com.shortener.shorturl.domain.urlShortener.url.Url;
import com.shortener.shorturl.infrastructure.message.LogMessageSender;
import com.shortener.shorturl.infrastructure.persistence.CacheService;
import java.time.LocalDateTime;
import java.time.ZoneId;
import javax.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ShortenerFacade {

    private final ShortenerService service;
    private final CacheService<Url> cacheService;
    private final LogMessageSender logMessageSender;
    private final ObjectMapper mapper;

    public String getRedirectUrl(String shortValue) throws JsonProcessingException {
        // TODO cache -> AOP
        if (cacheService.exist(shortValue)) {
            return cacheService.get(shortValue).getOriginalUrl();
        }
        Url originalUrl = service.findOriginalUrl(shortValue);
        cacheService.set(originalUrl, 60);  // update?

        logMessageSender.send(
            mapper.writeValueAsString(
                UrlVisitLog.builder()
                    .shortValue(shortValue)
                    .redirectUrl(originalUrl.getOriginalUrl())
                    .createdAt(LocalDateTime.now(ZoneId.of("Asia/Seoul")))
                    .build()));

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
        if (command.getSize() > 10) {
            throw new TooManyShortUrlRequestException(
                "Too Many Short Url Request, Recommended: less than 10");
        }
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
