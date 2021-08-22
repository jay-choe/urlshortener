package com.visitor.urlshortener.service;

import com.visitor.urlshortener.entity.ApiKey;
import com.visitor.urlshortener.repository.ApiKeyRepository;
import java.util.Optional;
import javax.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class ApiKeyService {

    @Value("${api-key}")
    private String apiKey;

    private final ApiKeyRepository apiKeyRepository;

    public ApiKeyService(ApiKeyRepository apiKeyRepository) {
        this.apiKeyRepository = apiKeyRepository;
    }

    public boolean validateKey(String key) {
        if (key == null) {
            log.error("Api key is null");
            return false;
        }
        Optional<ApiKey> foundKey = apiKeyRepository.findById(key);
        if (foundKey.isEmpty()) {
            log.error("Not a valid key");
            return false;
        }
        log.info("Valid key");
        return true;
    }


    @PostConstruct
    private void syncApiKey() {
        if (!apiKeyRepository.existsById(apiKey)) {
            ApiKey apiKeyInstance = new ApiKey(apiKey);
            apiKeyRepository.save(apiKeyInstance);
        }
    }
}
