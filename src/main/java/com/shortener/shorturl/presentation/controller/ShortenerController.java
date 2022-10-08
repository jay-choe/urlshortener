package com.shortener.shorturl.presentation.controller;

import com.shortener.common.request.CreateShortUrlRequest;
import com.shortener.common.response.ApiResponse;
import com.shortener.shorturl.application.shortUrl.dto.CreateCustomUrlCommand;
import com.shortener.common.request.MultiShortUrlRequest;
import com.shortener.shorturl.application.shortUrl.dto.CreateShortUrlCommand;
import com.shortener.shorturl.application.shortUrl.dto.CreateShortUrlListCommand;
import com.shortener.shorturl.application.shortUrl.dto.ShortUrlResponse;
import com.shortener.shorturl.application.shortUrl.dto.ShortUrlListResponse;
import com.shortener.shorturl.application.shortUrl.service.ShortenerFacade;
import com.shortener.shorturl.presentation.dto.CreateCustomUrlRequest;
import java.io.IOException;
import javax.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
public class ShortenerController {

    private final ShortenerFacade shortenerFacade;

    @GetMapping("/")
    public String initialMessage() {
        return "단축 Url 서비스 입니다";
    }

    @GetMapping("/{value}")
    public void redirectToOriginalUrl(@PathVariable String value, HttpServletResponse response)
        throws IOException {
        log.info("value is {}", value);
        String redirectUrl = shortenerFacade.getRedirectUrl(value);
        response.setHeader("Location", redirectUrl);
        log.info("Redirect URL is :{}", redirectUrl);
        response.setStatus(HttpStatus.FOUND.value());
        response.sendRedirect(redirectUrl);
    }

    @PostMapping("/urls")
    public ResponseEntity<?> createShortUrl(CreateShortUrlRequest request) throws Exception {
        log.info("Original url is {}", request.getOriginalUrl());
        String shortUrl = shortenerFacade.createShortUrl(CreateShortUrlCommand.of(request));
        log.info("shortUrl is {}", shortUrl);
        return new ResponseEntity<>(ShortUrlResponse.builder()
            .originalUrl(request.getOriginalUrl())
            .shortUrl(shortUrl)
            .build(), HttpStatus.CREATED);
    }

    @PostMapping("/multi-urls")
    public ResponseEntity<?> createShortUrls(@RequestBody MultiShortUrlRequest request) {
        log.info("CreateShortUrls Called");
        log.info("Contents: {}", request);
        CreateShortUrlListCommand command = CreateShortUrlListCommand.of(request);
        ShortUrlListResponse shortUrls = shortenerFacade.createShortUrlByMultiRequest(command);
        log.info("Short URLs are {}", shortUrls);
        return new ResponseEntity<>(shortUrls, HttpStatus.CREATED);
    }

    @PostMapping("/custom-url")
    public ApiResponse<?> createCustomUrl(@RequestBody CreateCustomUrlRequest request) {
        log.info("Create Custom URL Request");
        CreateCustomUrlCommand command = CreateCustomUrlCommand.of(request.getOriginUrl(), request.getTarget());
        String customUrl = shortenerFacade.createCustomUrl(command);
        return ApiResponse.of("2010", customUrl);
    }
}
