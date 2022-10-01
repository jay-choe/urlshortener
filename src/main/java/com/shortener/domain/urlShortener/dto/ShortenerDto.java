package com.shortener.domain.urlShortener.dto;

import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ShortenerDto implements Serializable {
        private String id;
        private String originalUrl;
}
