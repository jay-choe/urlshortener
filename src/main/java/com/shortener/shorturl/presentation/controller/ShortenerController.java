package com.shortener.shorturl.presentation.controller;

import com.shortener.common.response.ApiResponse;
import com.shortener.shorturl.application.shortUrl.dto.CreateCustomUrlCommand;
import com.shortener.common.request.MultiShortUrlRequest;
import com.shortener.shorturl.application.shortUrl.dto.CreateShortUrlListCommand;
import com.shortener.shorturl.application.shortUrl.dto.ShortenerResponseDto;
import com.shortener.shorturl.application.shortUrl.dto.UrlResponseListDto;
import com.shortener.shorturl.domain.apiKey.service.ApiKeyService;
import com.shortener.shorturl.application.shortUrl.service.ShortenerFacade;
import com.shortener.shorturl.application.shortUrl.service.ShortenerService;
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
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
public class ShortenerController {

    private final ShortenerFacade shortenerFacade;
    private final ShortenerService shortenerService;
    private final ApiKeyService apiKeyService;

    @GetMapping("/")
    public String initialMessage() {
        return "단축 Url 서비스 입니다";
    }

    @GetMapping("/{value}")
    public void redirectToOriginalUrl(@PathVariable String value, HttpServletResponse response)
        throws IOException {
        log.info("value is {}", value);
        String redirectUrl = shortenerService.findOriginalUrl(value);
        log.info("Redirect URL is :{}", redirectUrl);
        response.setStatus(HttpStatus.FOUND.value());
        response.sendRedirect(redirectUrl);
    }

    @PostMapping("/urls")
    public ResponseEntity<?> createShortUrl(String originalUrl,
        @RequestHeader(value = "X-API-KEY") String value) throws Exception {
        log.info("CreateShortUrl");
        log.info("Original url is {}", originalUrl);
        boolean validated = apiKeyService.validateKey(value);
        if (!validated) {
            return new ResponseEntity<>("API-Key가 유효하지 않습니다", HttpStatus.UNAUTHORIZED);
        }
        String shortUrl = shortenerService.createShortUrl(originalUrl);
        log.info("shortUrl is {}", shortUrl);
        return new ResponseEntity<>(new ShortenerResponseDto(originalUrl, shortUrl), HttpStatus.CREATED);
    }

    @PostMapping("/multi-url")
    public ResponseEntity<?> createShortUrls(@RequestBody MultiShortUrlRequest request
    , @RequestHeader(value = "X-API-KEY") String value) throws Exception {
        log.info("CreateShortUrls Called");
        log.info("Contents: {}", request);
        // TODO : validation -> detach
        boolean validated = apiKeyService.validateKey(value);

        if (!validated) {
            return new ResponseEntity<>("API-Key가 유효하지 않습니다", HttpStatus.UNAUTHORIZED);
        }


        // TODO: Presentation -> facade 계층 의존성 제거 (command)
        CreateShortUrlListCommand command = CreateShortUrlListCommand.of(request);
        UrlResponseListDto shortUrls = shortenerService.createShortUrl(request.getUrlList());
        log.info("Short URLs are {}", shortUrls);
        return new ResponseEntity<>(shortUrls, HttpStatus.CREATED);
    }

    @PostMapping("/custom-url")
    public ApiResponse<?> createCustomUrl(@RequestBody CreateCustomUrlRequest request) {
        log.info("Create Custom URL Request");
        CreateCustomUrlCommand command = CreateCustomUrlCommand.of(request.getOriginUrl(), request.getTarget());
        String customUrl = shortenerFacade.createCustomUrl(command);
        return ApiResponse.of("2000", customUrl);
    }
}
