package com.shortener.shorturl.domain.urlShortener.repository;

import com.shortener.shorturl.domain.urlShortener.entity.Url;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UrlRepository extends JpaRepository<Url, String> {
}
