package com.shortener.shorturl.application.shortUrl.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;

import com.shortener.shorturl.application.shortUrl.exception.AlreadyExistException;
import com.shortener.shorturl.domain.urlShortener.url.Url;
import com.shortener.shorturl.infrastructure.persistence.UrlRepository;
import java.util.Optional;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

@TestInstance(Lifecycle.PER_CLASS)
public class ShortenerServiceTest {

    @Mock
    UrlRepository repository;

    @InjectMocks
    ShortenerService service;

    @BeforeAll
    void init() {
        MockitoAnnotations.openMocks(this);

        Url url = Url.builder()
            .originalUrl("http://localhost/")
            .address("test")
            .build();

        given(repository.findById("test"))
            .willReturn(Optional.ofNullable(url));

        given(repository.save(url))
            .willReturn(url);
    }

    @Test
    void checkAlreadyExistTarget() {
        assertThrows(AlreadyExistException.class, () -> service.checkAlreadyExistAddress("test"));
    }

    @Test
    void createCustomUrl() {
        String customUrl = service.createCustomUrl("http://localhost/", "test");
        assertEquals("test", customUrl);
    }
}