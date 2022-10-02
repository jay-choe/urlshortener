package com.shortener.shorturl.domain.apiKey.service;

import com.shortener.common.util.ShortenerUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class ApiKeyService {

    @Value("${secretKey}")
    private String secretKey;

    private final ShortenerUtil util;

    public ApiKeyService(ShortenerUtil util) {
        this.util = util;
    }

    public boolean validateKey(String key) throws Exception {
        if (key == null) {
            log.error("Api key is null");
            return false;
        }
        if (!key.contains(".")) {
            log.error("Invalid Api key format");
            return false;
        }
        String separateKey[] = key.split("\\.");
        String encodedValue = separateKey[0];
        String encryptedValue = separateKey[1];
        try {
            if (util.decryptApiKey(encryptedValue, secretKey).equals(encodedValue)) {
                log.info("Valid key");
                return true;
            }
        } catch (Exception e) {
            log.error("Invalid Api Key");
        }
        return false;
    }
}
