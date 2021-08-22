package com.visitor.urlshortener.service;

import com.visitor.urlshortener.entity.ApiKey;
import com.visitor.urlshortener.repository.ApiKeyRepository;
import javax.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class ApiKeyService {

    @Value("${api-key}")
    private String apiKey;

    private final ApiKeyRepository apiKeyRepository;

    public ApiKeyService(ApiKeyRepository apiKeyRepository) {
        this.apiKeyRepository = apiKeyRepository;
    }


    @PostConstruct
    private void syncApiKey() {
        if (!apiKeyRepository.existsById(apiKey)) {
            ApiKey apiKeyInstance = new ApiKey(apiKey);
            apiKeyRepository.save(apiKeyInstance);
        }
    }
}
