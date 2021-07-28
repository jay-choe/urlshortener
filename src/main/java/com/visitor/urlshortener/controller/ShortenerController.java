package com.visitor.urlshortener.controller;

import com.visitor.urlshortener.dto.shortenerResponseDto;
import com.visitor.urlshortener.service.ShortenerService;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import javax.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ShortenerController {

    private final ShortenerService shortenerService;

    @GetMapping("/{hashValue}")
    public void redirectToOriginalUrl(@PathVariable String hashValue, HttpServletResponse response)
        throws IOException {
        String redirectUrl = shortenerService.findOriginalUrl(hashValue);
        response.sendRedirect(redirectUrl);
    }

    @PostMapping("/url")
    public ResponseEntity<?> createShortUrl(String originalUrl) throws NoSuchAlgorithmException {
        String shortUrl = shortenerService.createShortUrl(originalUrl);
        return new ResponseEntity<>(new shortenerResponseDto(shortUrl, originalUrl), HttpStatus.CREATED);
    }
}
