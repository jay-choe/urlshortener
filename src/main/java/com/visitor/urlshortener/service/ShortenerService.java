package com.visitor.urlshortener.service;

import com.visitor.urlshortener.entity.Url;
import com.visitor.urlshortener.repository.UrlRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ShortenerService {
    public final UrlRepository urlRepository;
    private final String errorRedirectUrl = "https://visitor.dev.42seoul.io";

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
}
