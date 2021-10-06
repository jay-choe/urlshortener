package com.visitor.urlshortener.repository;

import com.visitor.urlshortener.entity.Url;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UrlRepository extends JpaRepository<Url, String> {
}
