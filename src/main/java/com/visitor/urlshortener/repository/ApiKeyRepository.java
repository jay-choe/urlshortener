package com.visitor.urlshortener.repository;

import com.visitor.urlshortener.entity.ApiKey;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ApiKeyRepository extends JpaRepository<ApiKey, String> {

}
