package com.shortener.shorturl.application.shortUrl.integration;

import static org.assertj.core.api.Assertions.assertThat;

import com.shortener.shorturl.application.shortUrl.annotation.IntegrationTest;
import com.shortener.shorturl.application.shortUrl.service.ShortenerFacade;
import com.shortener.shorturl.domain.urlShortener.url.Url;
import com.shortener.shorturl.infrastructure.persistence.UrlRepository;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.springframework.beans.factory.annotation.Autowired;

@IntegrationTest
@TestInstance(Lifecycle.PER_CLASS)
public class ShortenerIntegrationTest {

    @Autowired
    ShortenerFacade shortenerFacade;

    @Autowired
    UrlRepository repository;

    @BeforeAll
    void init() {
        repository.save(Url.builder()
            .originalUrl("http://test.com")
            .address("custom")
            .build());
    }

    @AfterAll
    void clean() {
        repository.deleteAll();
    }

    @Test
    void findOriginalUrlTest() {
        assertThat(shortenerFacade.getRedirectUrl("custom"))
            .isEqualTo("http://test.com");
    }
}
