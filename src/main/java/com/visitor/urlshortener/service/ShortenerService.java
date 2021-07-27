package com.visitor.urlshortener.service;

import com.visitor.urlshortener.repository.UrlRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ShortenerService {
    public final UrlRepository urlRepository;
}
