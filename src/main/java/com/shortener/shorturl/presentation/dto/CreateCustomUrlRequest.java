package com.shortener.shorturl.presentation.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class CreateCustomUrlRequest {
    private String originUrl;
    private String target;
}
