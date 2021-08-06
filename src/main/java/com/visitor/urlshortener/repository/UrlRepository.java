package com.visitor.urlshortener.repository;

import com.visitor.urlshortener.entity.Url;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface UrlRepository extends JpaRepository<Url, String> {
}
