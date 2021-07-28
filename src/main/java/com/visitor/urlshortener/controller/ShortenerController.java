package com.visitor.urlshortener.controller;

import com.visitor.urlshortener.dto.ShortenerDto;
import com.visitor.urlshortener.dto.ShortenerResponseDto;
import com.visitor.urlshortener.dto.UrlCreateDto;
import com.visitor.urlshortener.dto.UrlResponseDto;
import com.visitor.urlshortener.service.ShortenerService;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.List;
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

    private final ShortenerService shortenerService;

    @GetMapping("/{hashValue}")
    public void redirectToOriginalUrl(@PathVariable String hashValue, HttpServletResponse response)
        throws IOException {
        log.info("hash value is {}", hashValue);
        String redirectUrl = shortenerService.findOriginalUrl(hashValue);
        response.sendRedirect(redirectUrl);
    }

    @PostMapping("/url")
    public ResponseEntity<?> createShortUrl(String originalUrl) throws NoSuchAlgorithmException {
        log.info("CreateShortUrl");
        log.info("Original url is {}", originalUrl);
        String shortUrl = shortenerService.createShortUrl(originalUrl);
        return new ResponseEntity<>(new ShortenerResponseDto(originalUrl, shortUrl), HttpStatus.CREATED);
    }

    @PostMapping("/urls")
    public ResponseEntity<List<UrlResponseDto>> createShortUrls(@RequestBody UrlCreateDto urlCreateDto) {
        log.info("CreateShortUrls Called");
        log.info("Contents: {}", urlCreateDto);
        return new ResponseEntity<>(shortenerService.createShortUrl(urlCreateDto.getUrlList()), HttpStatus.CREATED);
    }
}
