package com.visitor.urlshortener.controller;

import com.visitor.urlshortener.dto.ShortenerResponseDto;
import com.visitor.urlshortener.dto.UrlCreateDto;
import com.visitor.urlshortener.dto.UrlResponseListDto;
import com.visitor.urlshortener.service.ApiKeyService;
import com.visitor.urlshortener.service.ShortenerService;
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
        response.setStatus(HttpStatus.MOVED_PERMANENTLY.value());
        response.sendRedirect(redirectUrl);
    }

    @PostMapping("/url")
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

    @PostMapping("/urls")
    public ResponseEntity<?> createShortUrls(@RequestBody UrlCreateDto urlCreateDto
    , @RequestHeader(value = "X-API-KEY") String value) throws Exception {
        log.info("CreateShortUrls Called");
        log.info("Contents: {}", urlCreateDto);
        boolean validated = apiKeyService.validateKey(value);
        if (!validated) {
            return new ResponseEntity<>("API-Key가 유효하지 않습니다", HttpStatus.UNAUTHORIZED);
        }
        UrlResponseListDto shortUrls = shortenerService.createShortUrl(urlCreateDto.getUrlList());
        log.info("Short URLs are {}", shortUrls);
        return new ResponseEntity<>(shortUrls, HttpStatus.CREATED);
    }
}
