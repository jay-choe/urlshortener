package com.shortener.shorturl.application.shortUrl.service;

import com.shortener.shorturl.application.shortUrl.dto.CreateShortUrlListCommand;
import com.shortener.shorturl.application.shortUrl.dto.ShortUrlListResponse.UrlWithIdentifier;
import com.shortener.shorturl.application.shortUrl.exception.AlreadyExistException;
import com.shortener.shorturl.infrastructure.persistence.UrlRepository;
import com.shortener.common.util.Base62;
import com.shortener.shorturl.application.shortUrl.dto.ShortUrlListResponse;
import com.shortener.shorturl.domain.urlShortener.url.Url;
import com.shortener.common.util.ShortenerUtil;
import java.math.BigInteger;
import java.security.NoSuchAlgorithmException;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class ShortenerService {
    public final UrlRepository urlRepository;

    @Value("${error-redirect-url}")
    private String errorRedirectUrl;

    public ShortenerService(UrlRepository urlRepository) {
        this.urlRepository = urlRepository;
    }

    public String findOriginalUrl(String encodedValue) {
        boolean isValidStr = Base62.checkInvalidCharacter(encodedValue);

        if (!isValidStr) {
            log.error("잘못된 인코딩 값: {}", encodedValue);
            return errorRedirectUrl;
        }
        String originalHash = Base62.decoding(encodedValue);

        if (originalHash.length() != 10) {
            log.error("원본 해시길이가 아닙니다 {}", originalHash);
            return errorRedirectUrl;
        }

        Optional<Url> foundUrl = urlRepository.findById(originalHash);
        if (foundUrl.isEmpty()) {
            log.error("Url is not found");
            return errorRedirectUrl;
        }
        return foundUrl.get().getOriginalUrl();
    }

    public String createShortUrl(String originalUrl) throws NoSuchAlgorithmException {
        String hashValue = ShortenerUtil.encrypt(originalUrl);
        String value = hashValue.substring(0, 10);
        Url url = new Url(value, originalUrl);
        try {
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
        BigInteger bigInteger = new BigInteger(value, 16);
        String encoding = Base62.encoding(bigInteger);
        log.info("Encoded with Base62 value is {}", encoding);
        return encoding;
    }

    public ShortUrlListResponse createShortUrl(CreateShortUrlListCommand command) {
        return ShortUrlListResponse.builder()
            .urlList(command.getUrlList()
            .entrySet()
            .stream()
            .map(url -> {
                String value = null;
                try {
                    value = createShortUrl(url.getValue());
                } catch (NoSuchAlgorithmException e) {
                    log.error(e.getMessage());
                }
                return UrlWithIdentifier.builder()
                    .id(url.getKey())
                    .shortUrl(value)
                    .build();
            })
            .collect(Collectors.toList()))
            .build();
    }

    public void checkAlreadyExistTarget(String url) {
        urlRepository.findById(url)
            .ifPresent((foundUrl) -> {
                throw new AlreadyExistException(foundUrl + "은 이미 존재합니다");
            });
    }

    public String createCustomUrl(String originUrl, String targetUrl) {
        Url customUrl = urlRepository.save(Url.builder()
            .originalUrl(originUrl)
            .target(targetUrl)
            .build());

        return customUrl.getTarget();
    }
}
