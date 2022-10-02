package com.shortener.shorturl.infrastructure.persistence;

import com.shortener.shorturl.domain.urlShortener.url.Url;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UrlRepository extends JpaRepository<Url, String> {
}
