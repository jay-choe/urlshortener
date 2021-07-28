package com.visitor.urlshortener.service;

import com.visitor.urlshortener.entity.Url;
import com.visitor.urlshortener.repository.UrlRepository;
import com.visitor.urlshortener.util.ShortenerUtil;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class ShortenerService {
    public final UrlRepository urlRepository;
    public final ShortenerUtil shortenerUtil;

    private final String errorRedirectUrl = "https://visitor.dev.42seoul.io";

    public ShortenerService(UrlRepository urlRepository,
        ShortenerUtil shortenerUtil) {
        this.urlRepository = urlRepository;
        this.shortenerUtil = shortenerUtil;
    }

    public String findOriginalUrl(String hashValue) {
        List<Url> urlList = urlRepository.searchByHashValueStartsWith(hashValue);
        if (urlList == null) {
            return errorRedirectUrl;
        }
        return urlList.stream()
            .map(url -> url.getOriginalUrl().toString())
            .filter(url -> 1 == 1)
            .findFirst().orElse(errorRedirectUrl);
    }

    public String createShortUrl(String originalUrl) throws NoSuchAlgorithmException {
        String hashValue = shortenerUtil.encrypt(originalUrl);
        Url url = new Url(hashValue, originalUrl);
        urlRepository.save(url);
        return hashValue.substring(0, 10);
    }
}
