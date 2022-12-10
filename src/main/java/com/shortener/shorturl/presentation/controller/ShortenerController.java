package com.shortener.shorturl.presentation.controller;

import static com.shortener.common.enums.ApiResponseCode.CREATED_SUCCESS;

import com.shortener.common.request.CreateShortUrlRequest;
import com.shortener.common.response.ApiResponse;
import com.shortener.shorturl.application.shortUrl.dto.command.CreateCustomUrlCommand;
import com.shortener.common.request.MultiShortUrlRequest;
import com.shortener.shorturl.application.shortUrl.dto.command.CreateShortUrlCommand;
import com.shortener.shorturl.application.shortUrl.dto.command.CreateShortUrlListCommand;
import com.shortener.shorturl.application.shortUrl.dto.response.ShortUrlResponse;
import com.shortener.shorturl.application.shortUrl.service.ShortenerFacade;
import com.shortener.shorturl.presentation.dto.CreateCustomUrlRequest;
import java.io.IOException;
import javax.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
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
        setRedirectInfo(shortenerFacade.getRedirectUrl(value), response);
    }

    @PostMapping("/urls")
    public ApiResponse<?> createShortUrl(@RequestBody CreateShortUrlRequest request) {
        final String originalURL = request.getOriginalUrl();

        return ApiResponse.of(CREATED_SUCCESS,ShortUrlResponse.builder()
            .originalUrl(originalURL)
            .shortUrl(shortenerFacade.createShortUrl(CreateShortUrlCommand.of(request)))
            .build());
    }

    @PostMapping("/urls/custom-url")
    public ApiResponse<?> createCustomUrl(@RequestBody CreateCustomUrlRequest request) {
        CreateCustomUrlCommand command = CreateCustomUrlCommand.of(request.getOriginUrl(), request.getTarget());
        return ApiResponse.of(CREATED_SUCCESS, shortenerFacade.createCustomUrl(command));
    }

    @PostMapping("/urls/multi")
    public ApiResponse<?> createShortUrls(@RequestBody MultiShortUrlRequest request) {
        CreateShortUrlListCommand command = CreateShortUrlListCommand.of(request);
        return ApiResponse.of(CREATED_SUCCESS, shortenerFacade.createShortUrlByMultiRequest(command));
    }

    private void setRedirectInfo(String redirectURL, HttpServletResponse response)
        throws IOException {
        response.setHeader("Location", redirectURL);
        response.setStatus(HttpStatus.FOUND.value());
        response.sendRedirect(redirectURL);
    }
}
