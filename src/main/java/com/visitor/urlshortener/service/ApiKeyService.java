package com.visitor.urlshortener.service;

import com.visitor.urlshortener.util.ShortenerUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class ApiKeyService {

    @Value("${secret}")
    private String secret;

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
            log.error("Invalid Api key");
            return false;
        }
        String separateKey[] = key.split("\\.");
        String text = separateKey[0];
        String hash = separateKey[1];
        if (util.hmacEncrypt(text, secret).equals(hash)) {
            log.info("Valid key");
            return true;
        }
        log.error("Invalid Key Hash");
        return false;
    }
}
