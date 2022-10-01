package com.shortener.domain.urlShortener.repository;

import com.shortener.domain.urlShortener.entity.Url;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UrlRepository extends JpaRepository<Url, String> {
}
