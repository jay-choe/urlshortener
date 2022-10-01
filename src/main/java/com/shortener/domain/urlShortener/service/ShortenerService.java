package com.shortener.domain.urlShortener.service;

import com.shortener.domain.urlShortener.dto.ShortenerDto;
import com.shortener.domain.urlShortener.dto.UrlResponseDto;
import com.shortener.domain.urlShortener.repository.UrlRepository;
import com.shortener.common.util.Base62;
import com.shortener.domain.urlShortener.dto.UrlResponseListDto;
import com.shortener.domain.urlShortener.entity.Url;
import com.shortener.common.util.ShortenerUtil;
import java.math.BigInteger;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class ShortenerService {
    public final UrlRepository urlRepository;
    public final ShortenerUtil shortenerUtil;
    public final Base62 base62;

    private final String errorRedirectUrl = "https://visitor.dev.42seoul.io/error";

    public ShortenerService(UrlRepository urlRepository,
        ShortenerUtil shortenerUtil, Base62 base62) {
        this.urlRepository = urlRepository;
        this.shortenerUtil = shortenerUtil;
        this.base62 = base62;
    }

    public String findOriginalUrl(String encodedValue) {
        boolean isValidStr = base62.checkInvalidCharacter(encodedValue);

        if (!isValidStr) {
            log.error("잘못된 인코딩 값: {}", encodedValue);
            return errorRedirectUrl;
        }
        String originalHash = base62.decoding(encodedValue);

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
        String hashValue = shortenerUtil.encrypt(originalUrl);
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
        String encoding = base62.encoding(bigInteger);
        log.info("Encoded with base62 value is {}", encoding);
        return encoding;
    }

    public UrlResponseListDto createShortUrl(List<ShortenerDto> urlList) {
        return new UrlResponseListDto(urlList
            .stream()
            .map(url -> {
                String value = null;
                try {
                    value = createShortUrl(url.getOriginalUrl());
                } catch (NoSuchAlgorithmException e) {
                    log.error(e.getMessage());
                }
                return new UrlResponseDto(url.getId(), value);
            })
            .collect(Collectors.toList()));
    }
}
