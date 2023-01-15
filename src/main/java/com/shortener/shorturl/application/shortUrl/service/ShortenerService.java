package com.shortener.shorturl.application.shortUrl.service;

import com.shortener.common.enums.GenerateType;
import com.shortener.shorturl.application.shortUrl.dto.response.ShortUrlListResponse.UrlWithIdentifier;
import com.shortener.shorturl.application.shortUrl.exception.AlreadyExistException;
import com.shortener.shorturl.application.shortUrl.exception.URLNotFoundException;
import com.shortener.shorturl.application.shortUrl.service.generator.ShortURLGenerateStrategy;
import com.shortener.shorturl.application.shortUrl.service.generator.ShortURLGeneratorFactory;
import com.shortener.shorturl.infrastructure.persistence.UrlRepository;
import com.shortener.shorturl.application.shortUrl.dto.response.ShortUrlListResponse;
import com.shortener.shorturl.domain.urlShortener.url.Url;
import java.security.NoSuchAlgorithmException;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class ShortenerService {
    private final UrlRepository urlRepository;

    @Value("${error-redirect-url}")
    private String errorRedirectUrl;

    public ShortenerService(UrlRepository urlRepository) {
        this.urlRepository = urlRepository;
    }

    public Url findOriginalUrl(String shortUrl) {

        return urlRepository.findById(shortUrl)
            .orElseThrow(() -> new URLNotFoundException("URL is not found"));
    }

    public String createFixedShortURL(String originalUrl) {
        String hashValue = null;
        String value = null;
        final ShortURLGenerateStrategy fixedURLStrategy = ShortURLGeneratorFactory.getStrategy(GenerateType.FIXED);
        try {
            hashValue = fixedURLStrategy.create(originalUrl);
            value = hashValue.substring(0, 7);
            Url url = new Url(value, originalUrl);

            urlRepository.save(url);
        } catch (DataIntegrityViolationException exception) {
            log.error("Hash Collision Occured");
            int start = 0;
            int end = 0;

            while (urlRepository.existsById(value) && end <= 32) {
                start++;
                end = start + 10;
                value = hashValue.substring(start, end);
            }
            urlRepository.save(new Url(value, originalUrl));
        }

        log.info("Truncated Hash Value is {}", value);

        return value;
    }

    public ShortUrlListResponse createShortUrlList(Map<String, String> urlList) {
        return ShortUrlListResponse.builder()
            .urlList(urlList
            .entrySet()
            .stream()
            .map(url -> {
                String value = null;
                value = createFixedShortURL(url.getValue());
                return UrlWithIdentifier.builder()
                    .id(url.getKey())
                    .shortUrl(value)
                    .build();
            })
            .collect(Collectors.toList()))
            .build();
    }

    public void checkAlreadyExistAddress(String url) {
        urlRepository.findById(url)
            .ifPresent((foundUrl) -> {
                throw new AlreadyExistException(foundUrl + "은 이미 존재합니다");
            });
    }

    public String createCustomUrl(String originUrl, String address) {
        Url customUrl = urlRepository.save(Url.builder()
            .originalUrl(originUrl)
            .address(address)
            .build());

        return customUrl.getAddress();
    }
}
