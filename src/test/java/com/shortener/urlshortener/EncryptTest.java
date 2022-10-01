package com.shortener.urlshortener;

import com.shortener.common.util.ShortenerUtil;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class EncryptTest {


    ShortenerUtil util = new ShortenerUtil();

    @Test
    public void encryptTest() throws Exception {
        String secretKey = "1234567890abcdfe";
        String toEncrypt = "test";
        String encrypted = util.encryptApiKey(toEncrypt, secretKey);
        String decrypted = util.decryptApiKey(encrypted, secretKey);
        assertEquals(util.decryptApiKey(encrypted, secretKey), toEncrypt);
    }
}
