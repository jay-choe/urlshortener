package com.shortener.shorturl.application.shortUrl.service;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import com.shortener.shorturl.domain.urlShortener.url.Url;
import com.shortener.shorturl.infrastructure.cache.CacheService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

@TestInstance(Lifecycle.PER_CLASS)
class ShortenerFacadeTest {

    @Mock
    ShortenerService service;

    @Mock
    CacheService<Url> cacheService;

    @InjectMocks
    ShortenerFacade facade;

    @BeforeAll
    void init() {
        MockitoAnnotations.openMocks(this);

        final String address = "test";
        final String originalUrl = "http://test";
        final Url entity = Url.builder()
            .address("test")
            .originalUrl("http://test")
            .build();

        given(service.findOriginalUrl(address))
            .willReturn(entity);
    }

    @Test
    @DisplayName("단축 URL 조회는 캐싱레이어를 거친다")
    void getRedirectUrl() {
        final String address = "test";

        facade.getRedirectUrl(address);

        verify(cacheService, times(1)).set(Url.builder()
            .address(address)
            .originalUrl("http://test")
            .build(), 60);
    }
}