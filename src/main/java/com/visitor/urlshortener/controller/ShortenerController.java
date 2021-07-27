package com.visitor.urlshortener.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class ShortenerController {


    @GetMapping
    public void redirectToOriginalUrl(String hashValue) {

    }

    @PostMapping("/url")
    public ResponseEntity<?> createShortUrl(@RequestHeader("Authorization") String apiKeyHash, List<String> originalUrl) {

        return null;
    }
}
